// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
import java.util.*;

public class ApacheProfiler {
    
    public ApacheProfiler(){

        System.out.println("Welcome to Apache HTTP Profiler");

        Scanner scan=new Scanner(System.in);
        
        System.out.print("Website: "); String website=scan.nextLine();
        System.out.print("Test both http and https (Y/y or N/n): "); String test_both=scan.nextLine();
        System.out.print("Number of Threads: "); int num_threads=Integer.valueOf(scan.nextLine());
        System.out.print("Accuracy Level (1-100): "); int accuracy=Integer.valueOf(scan.nextLine());
        System.out.print("Payload From (Bytes): "); int payload_from=Integer.valueOf(scan.nextLine());
        System.out.print("Payload To (Bytes): "); int payload_to=Integer.valueOf(scan.nextLine());
        System.out.print("Interval Payload (Bytes): "); int interval=Integer.valueOf(scan.nextLine());
        System.out.print("Output filename: "); String outputfilename=scan.nextLine();

    }

    // public static void main(String args[]){
    //     Scanner scan=new Scanner(System.in);
    //     System.out.println("Welcome, \n Choose Type:\n 1: Apache Benchmark \n 2: Curl HTTP Profiler");
    //     String profiler_type=scan.nextLine();


    //     if(args.length >= 5){
            
    //         int num_threads=Integer.valueOf(args[0]);
    //         int proof=Integer.valueOf(args[1]);
    //         int payload=Integer.valueOf(args[2]);
    //         int interval=Integer.valueOf(args[3]);
    //         String outputfilename=args[4];
    //         String type="Apache";

    //         String site="http://bouer.local";
                
    //         if(type=="Apache"){
                
    //             site="https://10.5.1.42";
                
    //             // site="https://www.google.com";
                
    //             RunnableProfiler r_thread = new RunnableProfiler(type,"UnsafeThread-",proof,payload,interval,site,"results/"+outputfilename+"_unsafe_thread__.csv");
    //             r_thread.setThreadNumber(num_threads);
    //             r_thread.start();




    //         }else{

                    
    //                 for(int i=1; i<=num_threads; i++){
                        
    //                     RunnableProfiler r_thread = new RunnableProfiler(type, "UnsafeThread-"+i, proof,payload,interval,site,"results/"+outputfilename+"_unsafe_thread_"+i+"__.csv");
    //                     r_thread.start();
                    
    //                 }

    //                 site="https://bouer.local";
    //                 for(int i=1; i<=num_threads; i++){
                    
    //                     RunnableProfiler r_thread = new RunnableProfiler(type, "SafeThread-"+i, proof,payload,interval,site,"results/"+outputfilename+"_safe_thread_"+i+"__.csv");
    //                     r_thread.start();
                    
    //                 }     
    //         }

    //     }else{
            
    //         System.err.println("Usage: java CurlTest [threads:int] [precision_level:int] [payload_multiples:int] [interval:int] [outputfile:string]");
        
    //     } 
    // }
}