
/*
Maquina de Turing (MT)
Autor: Gabriel de Medeiros
12/09/2022
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class App {
    public static void main(String[] args) throws Exception {

        JSONObject jSONObject = new JSONObject();
        JSONParser parser = new JSONParser();
        
        //Trata o erro para args vazio
        try {

            jSONObject = (JSONObject) parser.parse(new FileReader(args[0]));
            JSONArray jsonMT = (JSONArray) jSONObject.get("mt");

            String palavra = "";

            if (args.length > 1) {
                palavra = args[1];
            }

            MaquinaTuring mt = new MaquinaTuring(jsonMT, palavra);

            if (mt.rodaMaq()) {
                System.out.print("Sim");
            } else {
                System.out.print("Não");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
