
package kylejohnsonM6;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
public class Label extends JLabel{
    
    Label(String text){
        super(text);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBackground(new Color(251, 233, 208));
        setForeground(Color.BLACK);
        
    }
}
