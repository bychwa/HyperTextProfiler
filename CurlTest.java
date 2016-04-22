/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// package diadrom.profiler;

/**
 *
 * @author jaxonisack
 */
public class CurlTest {
    
    public static void main(String args[]){
        
        if(args.length>=5){
            
            int num_threads=Integer.valueOf(args[0]);
            int proof=Integer.valueOf(args[1]);
            int payload=Integer.valueOf(args[2]);
            int interval=Integer.valueOf(args[3]);
            String outputfilename=args[4];

            String site="http://bouer.local";
            
            for(int i=1; i<=num_threads; i++){
                RunnableTest r_thread = new RunnableTest("UnsafeThread-"+i, proof,payload,interval,site,"results/"+outputfilename+"_unsafe_thread_"+i+"__.csv");
                r_thread.start();
            }

            site="https://bouer.local";
            for(int i=1; i<=num_threads; i++){
                RunnableTest r_thread = new RunnableTest("SafeThread-"+i, proof,payload,interval,site,"results/"+outputfilename+"_safe_thread_"+i+"__.csv");
                r_thread.start();
            }

        }else{
            System.err.println("Usage: java CurlTest [threads:int] [precision_level:int] [payload_multiples:int] [interval:int] [outputfile:string]");
        }
        
             
        
    }
}
