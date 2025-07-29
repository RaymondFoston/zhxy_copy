import com.example.zhxy.util.CreateVerifiCodeImage;
import javassist.expr.Instanceof;
import org.junit.jupiter.api.Test;


public class AppTest {
    @Test
    public void test1(){
        String verifiCode = CreateVerifiCodeImage.getVerifiCode();
        System.out.println(verifiCode);

    }
    @Test
    public void test2(){
        char[] c = {'a','f','d'};
        System.out.println(c.toString());
        System.out.println(c);
    }
    @Test
    public void test3(){
        String str = "";
//        System.out.println(str.equals(""));
//        System.out.println(str.isEmpty());
//        System.out.println("".equals(str));
        System.out.println(str == null || str.isEmpty());
    }

}
