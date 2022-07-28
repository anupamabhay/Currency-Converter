import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class CurrencyConversion {
    public static void main(String []args) throws IOException {
        //base url
        final var url = "https://api.apilayer.com/fixer/";
        //Build new client
        OkHttpClient client = null;
        try{
            client = new OkHttpClient().newBuilder().build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //New Scanner object to take user input
        Scanner scanner = new Scanner(System.in);
        String from = null, to = null;
        try{
            System.out.println("Enter the currency code from which you want to convert: ");
            from = scanner.next();
            System.out.println("Enter the target currency code: ");
            to = scanner.next();
            //convert user input to uppercase
            from = from.toUpperCase();
            to = to.toUpperCase();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //Conversion amount input
        System.out.println("Enter the amount you want to convert: ");
        var amount = scanner.nextInt();
        //Build New request
        Request request = new Request.Builder()
                .url(url+"convert?to="+to+"&from="+from+"&amount="+amount)
                .addHeader("apikey", "yourAPIkey") //replace 'yourAPIkey' with your Fixer API key
                .build();
        Response response = null;
        String message = null;
        try {
            //Send request and collect response
            response = client.newCall(request).execute();
            //convert response body to String
            message = response.body().string();
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //Check status of the response
        var responseCode = response.isSuccessful();
        if(responseCode){
            System.out.println("The response was collected successfully!");
        }
        else {
            System.out.println("Couldn't collect response!");
        }
        //Convert response body i.e. stored in var message (String) to JSON Object to access the result key
        JsonObject jObj = JsonParser.parseString(message).getAsJsonObject();
        //Print result
        System.out.println(amount + " " + from + " is equal to "+jObj.getAsJsonPrimitive("result")+" "+to);

    }
}
