import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

class TrieNode{
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
}
class Trie{
    TrieNode root =new TrieNode();
    // insert a vocabulary to Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.isEndOfWord = true;
    }
// if the Trie exist the vocabulary
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return false;
            }
        }
        return node.isEndOfWord;
    }
    public void clean(){
        root=null;
    }
}
public class TFIDFCalculator{
    public static double calculate_tf(String to_Find_String,String[] splited_text_section){
        double count=0;
        for(String segment : splited_text_section){
            //System.out.println(segment);
            if(to_Find_String.equals(segment)){
                count++;
            }
        }
        // System.out.println(splited_text_section.length);
        // System.out.println(count);
        double result=count/splited_text_section.length;
        System.out.println("tf="+result);
        return result;
    }
    public static double calculate_idf(ArrayList<String> text_sections,Double count){
        // double count =0;
        // double count_trie=0;
        // for(Trie trie : Trie_list){
        //     if(trie.search(to_Find_String)){
        //         count_trie++;
        //     }
        // }
        // for(String segment:text_sections){
        //     if(segment.indexOf(to_Find_String)!=-1){
        //         if((segment.substring(segment.indexOf(to_Find_String),segment.indexOf(to_Find_String)+to_Find_String.length())).equals(to_Find_String)){
        //             count++;
        //         }
        //     }
        
        // }
        double result=Math.log(text_sections.size()/count);
        System.out.println("udf="+result);

        return result;
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> text_section=new ArrayList<>();
            ArrayList<String> tc_lines_1=new ArrayList<>();
            ArrayList<Integer> tc_line_2=new ArrayList<>();

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(args[0]),"UTF-8");

            BufferedReader docs_reader = new BufferedReader(inputStreamReader);    //read parameter 0 : docs.txt
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
// there is one space at the first positon of each text!! take them out.
            for(int i=0;i<text_section.size();i++){
                text_section.set(i,(text_section.get(i)).trim());
                //System.out.println(text_section.get(i));
            }
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
//store the every text section in Trie and store all Tries in Obj Arraylist:
            ArrayList<Trie> Trie_list=new ArrayList<>();
            for(int i=0;i<text_section.size();i++){
                Trie the_tree= new Trie();
                for(String segment :(text_section.get(i)).split(" ")){
                    if(!the_tree.search(segment)){
                        the_tree.insert(segment);
                    }
                }
                Trie_list.add(the_tree);
            } 


//read the tc and calculate
            PrintWriter writer = new PrintWriter(new File("output.csv"));

            ArrayList<String> test=new ArrayList<>();
            ArrayList<Double> test_2=new ArrayList<>();


            for(int i =0;i<tc_lines_1.size();i++){
                if(!test.contains(tc_lines_1.get(i))){
                    Double count_trie=(double) 0;
                    for(Trie trie : Trie_list){
                        if(trie.search(tc_lines_1.get(i))){
                            count_trie++;
                        }
                    }
                    test.add(tc_lines_1.get(i));
                    test_2.add(count_trie);
                    
                }
                double tf_value=calculate_tf(tc_lines_1.get(i),(text_section.get(tc_line_2.get(i))).split(" "));
                double idf_value=calculate_idf(text_section,test_2.get(test.indexOf(tc_lines_1.get(i))));
                System.out.println(test_2.get(test.indexOf(tc_lines_1.get(i))));
                double result=(Math.round(tf_value*idf_value*100000.0)/100000.0);
                BigDecimal bd=new BigDecimal(result);
                writer.append(String.valueOf(bd.setScale(5, RoundingMode.HALF_UP)));
                writer.append(" ");
            }

            
            writer.close();

            // for(String segment : (text_section.get(1)).split(" ")){
            //     System.out.print(segment);
            //     System.out.print(" ");
            //     the_tree.insert(segment);
            // }


            // for(int i =0;i<tc_lines_1.size();i++){
            //     calculate_tf(tc_lines_1.get(i),(text_section.get(i)).split(" "));
            // }
            //double result=calculate_tf("the",(text_section.get(0)).split(" "));
            
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
