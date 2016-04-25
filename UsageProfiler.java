// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
import java.util.*;

public class UsageProfiler {

    private final String RESULTS_FOLDER_PATH="results/";
    private final String TEST_TYPE="USAGE";

    public UsageProfiler(){

        System.out.println("Welcome to Usage Profiler");

        Scanner scan=new Scanner(System.in);
        
        System.out.print("Number of Site Pages per normal usage: "); int num_pages=Integer.valueOf(scan.nextLine());
            
            String [] pages=new String[num_pages];
            for (int i=0;i < num_pages;i++ ) {
                System.out.print("Page "+i+":"); pages[i]=scan.nextLine();
            }

        // System.out.print("Website: "); String website=scan.nextLine();
        System.out.print("Test both http and https (Y/y or N/n): "); String test_both=scan.nextLine();
        
        String https_protocol="", cipher_file="";
        
        System.out.print("Number of Threads: "); int num_threads=Integer.valueOf(scan.nextLine());
        System.out.print("Accuracy Level (1-100): "); int accuracy=Integer.valueOf(scan.nextLine());
        System.out.print("Output filename: "); String outputfilename=scan.nextLine();

        RunnableProfiler s_thread,u_thread;
        // String unsafe_site="http://"+website+"/";
        // String safe_site="https://"+website+"/";
        String results_file_name="untouched.csv";
        Boolean secured=false; 
                 
        if(test_both.equals("Y")|| test_both.equals("y")){
          
            //unsafe thread
            secured=false;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread__.csv";
            u_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread", accuracy,num_threads,pages,results_file_name);
            u_thread.start();
            
            //safe thread
            secured=true;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_safe_thread__.csv";
            s_thread = new RunnableProfiler(TEST_TYPE,"SafeThread", accuracy,num_threads,pages,results_file_name);
            s_thread.start();
                
        
        }else{
            
            //unsafe thread
            secured=false;
            results_file_name=RESULTS_FOLDER_PATH+outputfilename+"_unsafe_thread__.csv";
            u_thread = new RunnableProfiler(TEST_TYPE,"UnsafeThread", accuracy,num_threads,pages,results_file_name);
            u_thread.start();

        }    
    }

}