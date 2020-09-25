/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam_card;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
/**
 *
 * @author EBUKA
 */
public class FXMLDocumentController implements Initializable { File file;   String path ,coll="1",depp="1"; 
  @FXML
    private VBox toastt;

    @FXML
    private JFXSpinner cind;

    @FXML
    private JFXSpinner pb;

    @FXML
    private VBox bar;

    @FXML
    private JFXTextArea TTXT;

    @FXML
    private ImageView picture_edge;

    @FXML
    private ImageView picture;
   
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        pb.setVisible(true);
        try {
            //       		 teracs_via_CMD();
//            ocr_binary();
ocr_binary_processed();
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public  void
    processImg(BufferedImage ipimage,  float scaleFactor, float offset) throws IOException, TesseractException 
    { 
        // Making an empty image buffer 
        // to store image later 
        // ipimage is an image buffer 
        // of input image 
        BufferedImage opimage 
            = new BufferedImage(1050,  1024, ipimage.getType()); 
  
        // creating a 2D platform 
        // on the buffer image 
        // for drawing the new image 
        Graphics2D graphic 
            = opimage.createGraphics(); 
  
        // drawing new image starting from 0 0 
        // of size 1050 x 1024 (zoomed images) 
        // null is the ImageObserver class object 
        graphic.drawImage(ipimage, 0, 0, 
                          1050, 1024, null); 
        graphic.dispose(); 
  
        // rescale OP object 
        // for gray scaling images 
        RescaleOp rescale 
            = new RescaleOp(scaleFactor, offset, null); 
  
        // performing scaling 
        // and writing on a .png file 
        BufferedImage fopimage 
            = rescale.filter(opimage, null); 
        ImageIO 
            .write(fopimage, 
                   "png", 
                   new File(path)); 
  
  
        // Instantiating the Tesseract class 
        // which is used to perform OCR 
        Tesseract it = new Tesseract(); 
  
        it.setDatapath("C:/Users/EBUKA/Downloads/Tess4J-3.4.8-src/Tess4J/tessdata"); 
  
  
        // doing OCR on the image 
        // and storing result in string str 
        String str = it.doOCR(fopimage); 
        System.out.println(str); 
        TTXT.setText(str);        pb.setVisible(false);
    } 
  
//    ilove programming bro  i dnt justkwy
    
    @FXML
    private void uploadclick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilterJPG = new  FileChooser.ExtensionFilter("JPG files (*.JPG)","*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new  FileChooser.ExtensionFilter("jpg files (*.jpg)","*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new  FileChooser.ExtensionFilter("PNG files (*.PNG)","*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new  FileChooser.ExtensionFilter("png files (*.png)","*.png");
        
        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterjpg,extFilterPNG,extFilterpng);
          file = fileChooser.showOpenDialog(null);
    path  =  file.getAbsolutePath();
   
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            picture.setImage(image);
        } catch (IOException ex) {
            }
    }
  
    
    
    
 public void ocr_binary_processed() throws IOException, TesseractException{
     File f 
            = new File(path); 
  
        BufferedImage ipimage = null; 
        try {
            ipimage = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        // getting RGB content of the whole image file 
        double d 
            = ipimage 
                  .getRGB(ipimage.getTileWidth() / 2, 
                          ipimage.getTileHeight() / 2); 
  
        // comparing the values 
        // and setting new scaling values 
        // that are later on used by RescaleOP 
        if (d >= -1.4211511E7 && d < -7254228) { 
            processImg(ipimage, 3f, -10f); 
        } 
        else if (d >= -7254228 && d < -2171170) { 
            processImg(ipimage, 1.455f, -47f); 
        } 
        else if (d >= -2171170 && d < -1907998) { 
            processImg(ipimage, 1.35f, -10f); 
        } 
        else if (d >= -1907998 && d < -257) { 
            processImg(ipimage, 1.19f, 0.5f); 
        } 
        else if (d >= -257 && d < -1) { 
            processImg(ipimage, 1f, 0.5f); 
        } 
        else if (d >= -1 && d < 2) { 
            processImg(ipimage, 1f, 0.35f); 
        } }
    public void ocr_binary() throws Exception {
        Tesseract tesseract = new Tesseract(); 
      tesseract.setDatapath("C:/Users/EBUKA/Downloads/Tess4J-3.4.8-src/Tess4J/tessdata"); 
  
            // the path of your tess data folder 
            // inside the extracted file 
            String text 
                = tesseract.doOCR(new File("C:\\Users\\EBUKA\\Documents\\NetBeansProjects\\Cam_Card\\src\\image\\test.png")); 
  
            // path of your image file 
            System.out.print(text); 
         
    }

   
    
    
    
    
    
    
    
     private void teracs_via_CMD() {
       		String input_file="C:\\Users\\EBUKA\\Documents\\NetBeansProjects\\Cam_Card\\src\\image\\download.PNG";
			String output_file="C:\\Users\\EBUKA\\Documents\\download";	
			String tesseract_install_path="C:\\Tesseract-OCR\\tesseract";
			String[] command =
		    {
		        "cmd",
		    };
		    Process p;
			try {
				p = Runtime.getRuntime().exec(command);
			        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
		                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
		                PrintWriter stdin = new PrintWriter(p.getOutputStream());
		                stdin.println("\""+tesseract_install_path+"\" \""+input_file+"\" \""+output_file+"\" -l eng");
		        	    stdin.close();
		                p.waitFor();
		                System.out.println();
		                System.out.println();
		                System.out.println();
		                System.out.println();
                                TTXT.setText(Read_File.read_a_file(output_file+".txt"));
		                System.out.println(Read_File.read_a_file(output_file+".txt"));
		    	} catch (Exception e) {
		 		e.printStackTrace();
			}
        
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
