import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.scoring_system.bean.Details;
import com.example.scoring_system.bean.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class DetailsTest {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream inputStream=new
                FileInputStream("E:\\其他\\三下\\软件工程\\团队作业\\阿尔法冲刺\\scoring_system\\src\\main\\resources\\static\\excel\\taskDetails.xls");
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(2);//header标题
        try {
            List<Details> details = ExcelImportUtil.importExcel(inputStream, Details.class, params);
            for (int i=0;i<details.size();i++)
            {
                System.out.println(details.get(i).toString());
//                for (int j=0;j<details.get(i).getScoreDetail().size();j++)
//                {
//                    System.out.println(details.get(i).getScoreDetail().get(j).toString());
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
