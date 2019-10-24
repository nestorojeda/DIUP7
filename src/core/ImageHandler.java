package core;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageHandler {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Mat toMat(BufferedImage bufferedImage) {
        Mat mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public static BufferedImage toBufferedImage(Mat mat){
        return (BufferedImage) HighGui.toBufferedImage(mat);
    }

    public static Mat thresholding (Mat originalImage, Integer threshold) {
        // crear dos imágenes en niveles de gris con el mismo tamaño que la original
        Mat greyImage = new Mat(originalImage.rows(), originalImage.cols(), CvType.CV_8U);
        Mat umbralizedImage = new Mat(originalImage.rows(), originalImage.cols(),
                CvType.CV_8U);

        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(originalImage, greyImage, Imgproc.COLOR_BGR2GRAY);

        // umbraliza la imagen:
        // ‐ píxeles con nivel de gris > umbral se ponen a 1
        // ‐ píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(greyImage, umbralizedImage, threshold, 255, Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return umbralizedImage;
    }
}
