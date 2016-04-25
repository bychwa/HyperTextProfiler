// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
import java.util.*;

public class ApacheProfiler {

    private final String RESULTS_FOLDER_PATH="results/";
    private final String TEST_TYPE="APACHE";

    public ApacheProfiler(){

        System.out.println("Welcome to Apache HTTP Profiler");

        Scanner scan=new Scanner(System.in);
        
        System.out.print("Website: "); String website=scan.nextLine();
        System.out.print("Test both http and https (Y/y or N/n): "); String test_both=scan.nextLine();
        
        String https_protocol="", cipher_file="";
        
        if(test_both.equals("Y")|| test_both.equals("y")){
        
            System.out.print("Specify SSL/TLS protocol (eg. TLS1.2, SSL3): "); https_protocol=scan.nextLine();
            System.out.print("Specify Cipher suite file URL: "); cipher_file=scan.nextLine();
        
        }
        
        System.out.print("Number of Threads: "); int num_threads=Integer.valueOf(scan.nextLine());
        System.out.print("Accuracy Level (1-100): "); int accuracy=Integer.valueOf(scan.nextLine());
        System.out.print("Output filename: "); String outputfilename=scan.nextLine();

        RunnableProfiler s_thread,u_thread;
        String unsafe_site="http://"+website+"/";
        String safe_site="https://"+website+"/";
        String results_file_name="untouched.csv";
        Boolean secured=false; 
                 
        if(test_both.equals("Y")|| test_both.equals("y")){
          
            //unsafe thread
            secured=false;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread__.csv";
            u_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread", accuracy,num_threads,unsafe_site,secured,https_protocol,cipher_file,results_file_name);
            u_thread.start();
            
            //safe thread
            secured=true;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_safe_thread__.csv";
            s_thread = new RunnableProfiler(TEST_TYPE,"SafeThread", accuracy,num_threads,safe_site,secured,https_protocol,cipher_file,results_file_name);
            s_thread.start();
                
        
        }else{
            
            //unsafe thread
            secured=false;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread__.csv";
            u_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread", accuracy,num_threads,unsafe_site,secured,https_protocol,cipher_file,results_file_name);
            u_thread.start();

        }    
    }

}