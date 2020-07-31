package kodex.pluginutils.model.steps;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.QRCode;

/** 
 * This class represents the bidirectional step between CharacterString and QRCode.
 * It contains the functionality to decode and encode the content between 
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */
public class CharacterStringToQRCode implements ChainStep {

  @Override
  public void decode(Content<?> input, Content<?> output) {
    CharacterString string = (CharacterString) output;
    QRCode qrcode = (QRCode) input;
    
    MultiFormatReader reader = new MultiFormatReader();
    try {
      string.setString(reader.decode(qrcode.getBinaryBitmap()).getText());
    } catch (NotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void encode(Content<?> input, Content<?> output) {
    CharacterString string = (CharacterString) input;
    QRCode qrcode = (QRCode) output;
    
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    //no additional padding
    int size = 0;
    try {
      BitMatrix bitMatrix = qrCodeWriter.encode(
          string.getString(), BarcodeFormat.QR_CODE, size, size);
      qrcode.setBitMatrix(bitMatrix);
    } catch (WriterException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}