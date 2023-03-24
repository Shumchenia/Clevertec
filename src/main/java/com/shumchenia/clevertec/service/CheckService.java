package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.model.check.builder.BaseCheckBuilder;
import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.model.product.DiscountProduct;
import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CheckService {

    private final BaseCheckBuilder checkBuilder;
    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;
    private static final String FILE = ".\\src\\main\\resources\\pdf\\";

    @Transactional
    public Check getCheck(List<Integer> productsId, String card) {
        Map<Product, Integer> products = getProducts(productsId);
        Check check = checkBuilder
                .addProduct(products)
                .addDiscountCard(discountCardRepository.findByCode(card))
                .build();
        try {
            createPdfFile(check);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    private void createPdfFile(Check check) throws IOException {
        File file = new File(FILE + "clever.pdf");
        PDDocument document = PDDocument.load(file);
        addMeta(document);
        addCheck(document, check);

        document.save(FILE + "check.pdf");
        document.close();
    }

    private static void addMeta(PDDocument document) {
        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setAuthor("Clevertec");
        pdd.setTitle("Check");
        pdd.setCreator("Shumchenia");
        pdd.setCreationDate(Calendar.getInstance());
        pdd.setModificationDate(Calendar.getInstance());
    }

    private void addCheck(PDDocument document, Check check) throws IOException {
        PDPage page = document.getPage(0);

        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImage(0);
        ImageIO.write(image, "JPEG", new File(FILE + "image.jpg"));

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject pdImage = PDImageXObject.createFromFile(FILE + "image.jpg", document);
        contentStream.drawImage(pdImage, 0, 0);

        contentStream.setLeading(30);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);

        contentStream.beginText();
        contentStream.newLineAtOffset(155, 550);
        contentStream.showText("Name");

        for (Product product : check.getProducts().keySet()) {
            contentStream.newLine();
            contentStream.showText(product.getName());
        }
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 550);
        contentStream.showText("Cost");
        for (Product product : check.getProducts().keySet()) {
            contentStream.newLine();
            if (product instanceof DiscountProduct)
                contentStream.showText(String.valueOf(product.getPrice()) + "*" + check.getProducts().get(product) + " " + "Sale");
            else
                contentStream.showText(String.valueOf(product.getPrice()) + "*" + check.getProducts().get(product));
        }
        contentStream.endText();

        int count = check.getProducts().size();

        contentStream.beginText();
        contentStream.newLineAtOffset(155, 550 - count * 54);
        contentStream.showText("Discount card:");
        contentStream.newLine();
        contentStream.showText("Total sum");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(350, 550 - count * 54);
        contentStream.showText("Salary " + (check.getDiscountCard().isPresent() ?
                check.getDiscountCard().get().getPercent() : "0"));
        contentStream.newLine();
        contentStream.showText(String.valueOf(check.getSum()));
        contentStream.endText();

        contentStream.close();
    }


    private Map<Product, Integer> getProducts(List<Integer> productsId) {
        Map<Product, Integer> products = new HashMap<>();
        for (Integer productId : productsId) {

            Product product = productRepository.findById(Long.valueOf(productId))
                    .orElseThrow(ProductNotFoundException::new);
            Integer count = Collections.frequency(productsId, Math.toIntExact(product.getId()));

            if (count > 5) {
                products.put(new DiscountProduct(product, 10d), count);
            } else {
                products.put(product, count);
            }
        }
        return products;
    }

}
