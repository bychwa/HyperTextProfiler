// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
import java.util.*;

public class HyperTextProfiler {
    private final String RESULTS_FOLDER_PATH="results/";
    private final String TEST_TYPE="CURL";
    public HyperTextProfiler(){

        System.out.println("Welcome to HTTP Profiler (with CURL)");

        Scanner scan=new Scanner(System.in);
        
        System.out.print("Website: "); String website=scan.nextLine();
        System.out.print("Test both http and https (Y/y or N/n): "); String test_both=scan.nextLine();
        System.out.print("Number of Threads: "); int num_threads=Integer.valueOf(scan.nextLine());
        System.out.print("Accuracy Level (1-100): "); int accuracy=Integer.valueOf(scan.nextLine());
        System.out.print("Payload From (Bytes): "); int payload_from=Integer.valueOf(scan.nextLine());
        System.out.print("Payload To (Bytes): "); int payload_to=Integer.valueOf(scan.nextLine());
        System.out.print("Interval Payload (Bytes): "); int interval=Integer.valueOf(scan.nextLine());
        System.out.print("Output filename: "); String outputfilename=scan.nextLine();

        String safe_site="https://"+website;
        String unsafe_site="http://"+website;
        String results_file_name="untouched.csv";                
        
        RunnableProfiler r_thread;

        if(test_both=="y" || test_both=="Y"){

            for(int i=1; i<=num_threads; i++){
                results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread_"+i+"__.csv";
                r_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread-"+i, accuracy,payload_from,payload_to,interval,unsafe_site,results_file_name);
                r_thread.start();
            
            }
            for(int i=1; i<=num_threads; i++){
            
                results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_safe_thread_"+i+"__.csv";
                r_thread = new RunnableProfiler(TEST_TYPE,"SafeThread-"+i, accuracy,payload_from,payload_to,interval,safe_site,results_file_name);
                r_thread.start();
        
            }

        }else{

            for(int i=1; i<=num_threads; i++){
                results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread_"+i+"__.csv";
                r_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread-"+i,accuracy,payload_from,payload_to,interval,unsafe_site,results_file_name);
                r_thread.start();
            
            }

        }
    }

    public static void main(String args[]){
        
        if(args.length >= 5){
            
            int num_threads=Integer.valueOf(args[0]);
            int proof=Integer.valueOf(args[1]);
            int payload=Integer.valueOf(args[2]);
            int interval=Integer.valueOf(args[3]);
            String outputfilename=args[4];

            String site="http://bouer.local";
            
            

        }else{
            
            System.err.println("Usage: java CurlTest [threads:int] [precision_level:int] [payload_multiples:int] [interval:int] [outputfile:string]");
        
        }
        
    }
}