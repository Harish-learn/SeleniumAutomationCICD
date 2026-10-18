package ecomaaplication.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

public class DataReader {

    public List<HashMap<String, String>> getJsonDataToMap() throws IOException {

        //Json to string
        String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"src//test//java//ecomaaplication//data//PurchaseOrder.json"));

        //string to hashmap - need json databind jar
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});

        return data;
    }
}
