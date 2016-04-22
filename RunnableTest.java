import java.io.*;
import java.util.Date;

class RunnableProfiler implements Runnable {
     
      private Thread t;
      private String threadName;
      private int proof,payload,interval;
      private String ofile,site;

      RunnableProfiler(String type, String threadName,int proof, int payload,int interval,  String site, String ofile){
         
         if(type=="Apache"){

            this.threadName = threadName;
              
         } else {
            this.threadName = threadName;
            this.proof=proof;
            this.payload=payload;
            this.ofile=ofile;
            this.site=site;
            this.interval=interval;
         }
          

      }

      public void run() {
          
          if(type=="Apache"){

                System.out.println("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp");
                

          }else{

              int proof_rounds=proof;
              int payload_iterations=payload;

              TerminalCommandExecutor se=new TerminalCommandExecutor();
                
              try {

                  File fout = new File(ofile);
                  FileOutputStream fos = new FileOutputStream(fout);
                  OutputStreamWriter osw = new OutputStreamWriter(fos);

                  System.out.println("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp");
                  osw.write("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp"+"\n");
                  
                  for(int i=1; i<=Integer.valueOf(payload_iterations);i++){
                        
                        String num_bytes=String.valueOf(interval * (i));
                        
                        String command="curl -o /dev/null --insecure -s -w %{time_connect},%{time_starttransfer},%{time_total},%{time_appconnect},%{time_namelookup},%{time_pretransfer},%{time_redirect} "+site+"/get_response?size="+num_bytes;
                        
                        double t_connect=0,t_transfer=0,t_total=0,t_appconnect=0,t_namelookup=0,t_pretransfer=0,t_redirect=0,tcp_start=0,tcp_end=0,tcp_time=0;
                        
                        for(int j=1; j<=proof_rounds;j++){
                            
                                tcp_start = new java.util.Date().getTime(); //start of the request
                            String results=se.runCommand(command,true);
                                tcp_end = new java.util.Date().getTime();   //end of the request
                            
                                tcp_time+=(tcp_end - tcp_start);

                            String[] resultsArray=results.split(",");
                            
                                t_connect+=Double.valueOf(resultsArray[0]);
                                t_transfer+=Double.valueOf(resultsArray[1]);
                                t_total+=Double.valueOf(resultsArray[2]);
                                t_appconnect+=Double.valueOf(resultsArray[3]);
                                t_namelookup+=Double.valueOf(resultsArray[3]);
                                t_pretransfer+=Double.valueOf(resultsArray[3]);
                                t_redirect+=Double.valueOf(resultsArray[3]);

                        }

                        System.out.println(threadName+", "+num_bytes+", "+String.format( "%.8f",(t_namelookup/proof_rounds))+", "+String.format( "%.8f",(t_appconnect/proof_rounds))+", "+String.format( "%.8f",(t_redirect/proof_rounds))+", "+String.format( "%.8f",(t_connect/proof_rounds) )+", "+String.format( "%.8f",(t_pretransfer/proof_rounds))+", "+String.format( "%.8f",(t_transfer/proof_rounds))+", "+String.format( "%.8f",(t_total/proof_rounds))+","+String.format("%.8f",(tcp_time/proof_rounds)/1000)+", "+new java.util.Date().getTime());
                        osw.write(threadName+", "+num_bytes+", "+String.format( "%.8f",(t_namelookup/proof_rounds))+", "+String.format( "%.8f",(t_appconnect/proof_rounds))+", "+String.format( "%.8f",(t_redirect/proof_rounds))+", "+String.format( "%.8f",(t_connect/proof_rounds) )+", "+String.format( "%.8f",(t_pretransfer/proof_rounds))+", "+String.format( "%.8f",(t_transfer/proof_rounds))+", "+String.format( "%.8f",(t_total/proof_rounds))+","+String.format("%.8f",(tcp_time/proof_rounds)/1000)+", "+new java.util.Date().getTime()+"\n");
                    
                    }

                    osw.close();

              }catch(Exception e){
                  
                  System.err.println(e); 
              
              }

          }
    }
   
   public void start ()
   {
      if (t == null){ t = new Thread (this, threadName); t.start (); }
   }

}