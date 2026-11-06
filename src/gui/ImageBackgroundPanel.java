/*
 * Custom JPanel that paints a background image with a dark overlay
 * so that text and controls remain readable.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Danils.Silovs
 */
public class ImageBackgroundPanel extends JPanel {

    private Image backgroundImage;
    private Color overlayColor = new Color(15, 23, 60, 160); // tumši zils, caurspīdīgs

    /**
     * Veido paneli ar norādītā attēla fonu.
     *
     * @param image attēls, ko zīmēt kā fonu (var būt null)
     */
    public ImageBackgroundPanel(Image image) {
        super();
        this.backgroundImage = image;
        setOpaque(false);
    }

    /**
     * Noklusējuma konstruktors (bez attēla) - vienkārši caurspīdīgs panels.
     */
    public ImageBackgroundPanel() {
        super();
        setOpaque(false);
    }

    /**
     * Ielādē attēlu no classpath resursa.
     *
     * Mēģina vairākus ceļus, lai būtu drošs pret dažādiem build/classpath
     * iestatījumiem. Nekad neizmet izņēmumu - ja attēls nav atrasts, atgriež null.
     *
     * @param resourcePath ceļš classpath, piem. "/resources/celojums_bg.jpg"
     * @return Image objekts vai null, ja resurss nav atrasts
     */
    public static Image loadImage(String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty()) {
            return null;
        }

        // Sagatavojam variantus - ar un bez sākuma slīpsvītras
        String[] variantsToTry;
        if (resourcePath.startsWith("/")) {
            variantsToTry = new String[]{
                resourcePath,
                resourcePath.substring(1)
            };
        } else {
            variantsToTry = new String[]{
                resourcePath,
                "/" + resourcePath
            };
        }

        for (String path : variantsToTry) {
            try {
                java.net.URL url = ImageBackgroundPanel.class.getResource(path);
                if (url != null) {
                    return new ImageIcon(url).getImage();
                }
                // Mēģinām arī caur ClassLoader
                java.net.URL url2 = Thread.currentThread().getContextClassLoader().getResource(path.startsWith("/") ? path.substring(1) : path);
                if (url2 != null) {
                    return new ImageIcon(url2).getImage();
                }
            } catch (Throwable t) {
                // ImageIcon konstruktorā var gadīties NPE, ja URL kļūst null pēc
                // pārbaudes (teorētiski nevar gadīties, bet drošības pēc)
                System.err.println("Brīdinājums: kļūda ielādējot attēlu '" + path + "': " + t.getMessage());
            }
        }

        // Mēģinām arī no failu sistēmas (project root / build / resources)
        String[] filesystemPaths = new String[]{
            "src/resources/celojums_bg.jpg",
            "resources/celojums_bg.jpg",
            "celojums_bg.jpg",
            "build/classes/resources/celojums_bg.jpg"
        };
        for (String fsPath : filesystemPaths) {
            try {
                java.io.File f = new java.io.File(fsPath);
                if (f.exists() && f.canRead()) {
                    return new ImageIcon(f.getAbsolutePath()).getImage();
                }
            } catch (Throwable t) {
                // ignorējam
            }
        }

        System.err.println("Brīdinājums: attēls netika atrasts: " + resourcePath);
        System.err.println("GUI tiks atvērts bez fona attēla (krāsainais dizains joprojām strādās).");
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Vispirms zīmējam fonu
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = backgroundImage.getWidth(this);
            int imageHeight = backgroundImage.getHeight(this);

            if (imageWidth > 0 && imageHeight > 0 && panelWidth > 0 && panelHeight > 0) {
                // Mērogojam attēlu, lai aizpildītu paneli (cover)
                double scaleX = (double) panelWidth / imageWidth;
                double scaleY = (double) panelHeight / imageHeight;
                double scale = Math.max(scaleX, scaleY);

                int scaledWidth = (int) Math.round(imageWidth * scale);
                int scaledHeight = (int) Math.round(imageHeight * scale);
                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;

                g2d.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);

                // Tumšs pārklājums, lai teksti būtu labi salasāmi
                g2d.setColor(overlayColor);
                g2d.fillRect(0, 0, panelWidth, panelHeight);
            }
            g2d.dispose();
        }
        // Tad zīmējam parastos komponentus virsū
        super.paintComponent(g);
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        repaint();
    }

    public Color getOverlayColor() {
        return overlayColor;
    }

    public void setOverlayColor(Color overlayColor) {
        this.overlayColor = overlayColor;
        repaint();
    }
}
