package data.json;

import data.download.APIDataServiceJson;
import data.download.APIDataServiceUrlImplJson;
import org.json.JSONException;
import org.junit.Test;
import po.StockPO;
import tool.constant.FilePath;
import tool.exception.BadInputException;
import tool.io.FileIOHelper;

import static org.junit.Assert.*;

/**
 * Created by kylin on 16/5/7.
 */
public class JsonReaderTest {

    private String mainPath = FilePath.CACHE_DIR;

    private APIDataServiceJson apiDataServiceJson = new APIDataServiceUrlImplJson();

    private JsonReader jsonReader = new JsonReader();


    @Test
    public void setAllFieldsName() throws Exception {
        String jsonResult = FileIOHelper.readTxtFile(mainPath + "availableFields.txt");
        jsonReader.setAllFieldsName(jsonResult);
    }

    @Test
    public void readAllStockNames() throws Exception {
        String jsonResult = FileIOHelper.readTxtFile(mainPath + "stockNumber/benchmarkNumbers.txt");
        jsonReader.readAllStockNames(jsonResult);
    }

    @Test
    public void readAllFields() throws Exception {
        String jsonResult = FileIOHelper.readTxtFile(mainPath + "availableFields.txt");
        jsonReader.readAllFields(jsonResult);
        try {
            jsonReader.readAllFields("123123");
        }catch (JSONException e){

        }
    }

    @Test
    public void readAllFieldsToString() throws Exception {
        String jsonResult = FileIOHelper.readTxtFile(mainPath + "availableFields.txt");
        jsonReader.readAllFieldsToString(jsonResult);
        jsonReader.readAllFieldsToString("123123");
    }

    @Test
    public void readStock() throws Exception {
        String jsonResult = FileIOHelper.readTxtFile(mainPath + "stockInfo/" + "sh600000" + ".txt");
        jsonReader.setAllFieldsName(FileIOHelper.readTxtFile(mainPath + "availableFields.txt"));
        try {
            StockPO po = jsonReader.readStock(jsonResult);
        }catch (BadInputException exception){

        }
    }

}