import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class TFIDFCalculator{
    public static void main(String[] args) {
        try {
            ArrayList<String> text_section=new ArrayList<>();
            ArrayList<String> tc_lines_1=new ArrayList<>();
            ArrayList<Integer> tc_line_2=new ArrayList<>();
            
            BufferedReader docs_reader = new BufferedReader(new FileReader(args[0]));    //read parameter 0 : docs.txt
            BufferedReader tc_reader = new BufferedReader(new FileReader(args[1]));
            String line_docs;  //the string lines in docs
            String line_tcs;
            String tem="";
            int docs_order=1;
//spilt the docs per 5 rows and merge them to a text stored in text_section
            while ((line_docs = docs_reader.readLine()) != null) {
                line_docs=((line_docs.replaceAll("[^a-zA-Z\\s]", " ")).replaceAll("\\s+"," ")).toLowerCase(); //regular expression :replace all the non-English char and multiple space to unit space
                tem=tem+line_docs;            
                if(docs_order%5==0){
                     text_section.add(tem.replaceAll("\\s+"," "));
                     tem="";
                }
                docs_order++;
            }
// there is one space at the first positon of each text!!
            // System.out.println(text_section.get(0));
            // System.out.println("------------------");
            // System.out.println(text_section.get(1));
            docs_reader.close();

//read the two rows of tc:
            line_tcs=(tc_reader.readLine()).replaceAll("\\s+"," ");
            for(String vocabulary : line_tcs.split(" ")){
                tc_lines_1.add(vocabulary);
            }
            line_tcs=(tc_reader.readLine()).replaceAll("\\s+"," ");
            for (String order : line_tcs.split(" ")){
                tc_line_2.add(Integer.valueOf(order));   //.valueOf because arrayList only can accept objects
            }
            tc_reader.close();
            
    
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
