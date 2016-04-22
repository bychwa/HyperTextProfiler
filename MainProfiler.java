// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
import java.util.*;

public class MainProfiler {
    
    public static void main(String args[]){

        Scanner scan=new Scanner(System.in);
        System.out.println("Welcome to Requests Profiler!, \n Choose Type:\n 1: Apache Benchmark \n 2: Curl HTTP Profiler \n");
        System.out.print("My choice is: "); 
        String profiler_type=scan.nextLine();

        switch(profiler_type){
            case "1":
                
                    new ApacheProfiler();

                break;

            case "2":
                    
                    new HyperTextProfiler();

                break;

            default:

                System.out.println(profiler_type+" is a wrong choice! \n Bye!");
                System.exit(1);
            
                break;    

        }    
        
        
    }
}