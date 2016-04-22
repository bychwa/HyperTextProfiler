import java.io.*;
import java.util.Date;

class RunnableProfiler implements Runnable {
     
      private Thread t;
      private String threadName;
      private int accuracy,payload_from,payload_to, interval;
      private String ofile,site,type;
      private int threadNumber=1;
      
      RunnableProfiler(String type, String threadName,int accuracy, int payload_from,int payload_to, int interval, String site, String ofile){
          
          this.type=type;
          this.threadName = threadName;
          this.accuracy=accuracy;
          this.payload_from=payload_from;
          this.payload_to=payload_to;
          this.interval=interval;
          this.site=site;
          this.ofile=ofile;
          

      }

      public void setThreadNumber(int tn){
  
          this.threadNumber=tn;
  
      }
      public void run() {
          
          TerminalCommandExecutor te=new TerminalCommandExecutor();
              
          switch(type){

              case "CURL":
                    
                    try {

                        File fout = new File(ofile);
                        FileOutputStream fos = new FileOutputStream(fout);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);

                        System.out.println("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp");
                        osw.write("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp"+"\n");
                        
                        for(int i=Integer.valueOf(payload_from); i <= Integer.valueOf(payload_to); i+=interval){
                              
                              String num_bytes=String.valueOf(i);
                              
                              String command="curl -o /dev/null --insecure -s -w %{time_connect},%{time_starttransfer},%{time_total},%{time_appconnect},%{time_namelookup},%{time_pretransfer},%{time_redirect} "+site+"/get_response?size="+num_bytes;
                              
                              double t_connect=0,t_transfer=0,t_total=0,t_appconnect=0,t_namelookup=0,t_pretransfer=0,t_redirect=0,tcp_start=0,tcp_end=0,tcp_time=0;
                              
                              for(int j=1; j<=accuracy;j++){
                                  
                                      tcp_start = new java.util.Date().getTime(); //start of the request
                                  String results=te.runCommand(command,true);
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

                              System.out.println(threadName+", "+num_bytes+", "+String.format( "%.8f",(t_namelookup/accuracy))+", "+String.format( "%.8f",(t_appconnect/accuracy))+", "+String.format( "%.8f",(t_redirect/accuracy))+", "+String.format( "%.8f",(t_connect/accuracy) )+", "+String.format( "%.8f",(t_pretransfer/accuracy))+", "+String.format( "%.8f",(t_transfer/accuracy))+", "+String.format( "%.8f",(t_total/accuracy))+","+String.format("%.8f",(tcp_time/accuracy)/1000)+", "+new java.util.Date().getTime());
                              osw.write(threadName+", "+num_bytes+", "+String.format( "%.8f",(t_namelookup/accuracy))+", "+String.format( "%.8f",(t_appconnect/accuracy))+", "+String.format( "%.8f",(t_redirect/accuracy))+", "+String.format( "%.8f",(t_connect/accuracy) )+", "+String.format( "%.8f",(t_pretransfer/accuracy))+", "+String.format( "%.8f",(t_transfer/accuracy))+", "+String.format( "%.8f",(t_total/accuracy))+","+String.format("%.8f",(tcp_time/accuracy)/1000)+", "+new java.util.Date().getTime()+"\n");
                          
                          }
                          System.out.println("------END-----");
                          osw.write("------END-----");
                        

                          osw.close();

                    }catch(Exception e){
                        System.err.println(e); 
                    }
                break;
            case "APACHE":
                    
                    String num_bytes=String.valueOf(interval * (10));
                    String command="ab -k -c "+this.threadNumber+" -n 100 "+site+"/get_response?size="+num_bytes;
                    String results=te.runCommand(command,true);
                    // System.out.println(results);
                    String[] lines = results.split(System.getProperty("line.separator"));
                    System.out.println("32 -> "+lines[32]);

                break;    

          }
         
      }
   
     public void start ()
     {
        if (t == null){ t = new Thread (this, threadName); t.start (); }
     }

}