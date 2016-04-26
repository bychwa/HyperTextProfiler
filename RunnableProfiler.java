import java.io.*;
import java.util.Date;

class RunnableProfiler implements Runnable {
     
      private Thread t;
      private String threadName,page;
      private int accuracy,payload_from,payload_to, interval;
      private String ofile,site,type,https_protocol,cipher_file;
      private int threadNumber=1;
      private Boolean secured;
      private String [] pages;
      private double tcp_start=0,tcp_end=0,tcp_time=0,micro_page_start=0,micro_page_end=0;
      private double[] tcp_micro_time;
      private File fout;
      private FileOutputStream fos;
      private OutputStreamWriter osw;
        
      RunnableProfiler(String type, String threadName,int accuracy, int threadNumber,Boolean secured, String[] pages, String ofile){
          
          //for Apache Benchmark
          this.threadNumber=threadNumber;
          this.type=type;
          this.threadName = threadName;
          this.accuracy=accuracy;
          this.pages=pages;
          this.ofile=ofile;
          this.secured=secured;
          
      }

      RunnableProfiler(String type, String threadName,int accuracy, int threadNumber, String site, Boolean secured, String https_protocol,String cipher_file, String ofile){
          
          //for Apache Benchmark
          this.threadNumber=threadNumber;
          this.type=type;
          this.threadName = threadName;
          this.accuracy=accuracy;
          this.site=site;
          this.https_protocol=https_protocol;
          this.cipher_file=cipher_file;
          this.ofile=ofile;
          this.secured=secured;

      }
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

      public void run() {
          
          TerminalCommandExecutor te=new TerminalCommandExecutor();
              
          switch(type){

              case "USAGE":

                      try {

                          fout = new File(ofile);
                          fos = new FileOutputStream(fout);
                          osw = new OutputStreamWriter(fos);
                          osw.write("SESSION:\t"+threadName+"\nNO PAGES:\t"+pages.length+"\n");
                            
                          for (int i=1; i <= accuracy; i++) {
                              osw.write("\tAccuracy Level:"+i+"\n");
                              
                              tcp_start = new java.util.Date().getTime(); //start of the request

                              for(int j=0; j < pages.length; j++){
                                  micro_page_start = new java.util.Date().getTime(); //start of the request
                                  page=secured?"https://"+pages[j]:"http://"+pages[j];
                                  String command="curl -o /dev/null --insecure -s -w %{time_connect},%{time_starttransfer},%{time_total},%{time_appconnect},%{time_namelookup},%{time_pretransfer},%{time_redirect} "+page;
                                  String results=te.runCommand(command,true);
                                  micro_page_end = new java.util.Date().getTime(); //start of the request
                                  osw.write("\t\tPAGE: "+pages[j]+"\t\t\t TIME:"+(micro_page_end-micro_page_start)/1000+" seconds\n");
                              
                              } 
                                  
                              tcp_end = new java.util.Date().getTime();   //end of the request
                              
                              tcp_time +=(tcp_end - tcp_start);
                              osw.write("\n");
                          }

                          System.out.println(threadName+","+pages[0]+", "+String.format("%.8f",(tcp_time/accuracy)/1000)+" seconds, "+new java.util.Date().getTime());
                            
                          osw.write("AVERAGE TIME:\t\t\t"+String.format("%.8f",(tcp_time/accuracy)/1000)+" seconds\n");
                          
                          osw.close();

                      }catch(Exception e){
                      
                          System.err.println("An error has occured while opening / writing to the file "+ofile);
                      
                      }
                          
                    break;

              case "CURL":
                    
                    try {

                          fout = new File(ofile);
                          fos = new FileOutputStream(fout);
                          osw = new OutputStreamWriter(fos);

                          System.out.println("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp");
                          osw.write("ThreadName, t_PayloadSize, t_NameLookup, t_AppConnect, t_Redirect, t_Connect, t_Pretransfer, t_Transfer, t_Total, t_OveralTotal, t_Timestamp"+"\n");
                        
                          for(int i=Integer.valueOf(payload_from); i <= Integer.valueOf(payload_to); i+=interval){
                                
                                String num_bytes=String.valueOf(i);
                                
                                String command="curl -o /dev/null --insecure -s -w %{time_connect},%{time_starttransfer},%{time_total},%{time_appconnect},%{time_namelookup},%{time_pretransfer},%{time_redirect} "+site+"/get_response?size="+num_bytes;
                                
                                double t_connect=0,t_transfer=0,t_total=0,t_appconnect=0,t_namelookup=0,t_pretransfer=0,t_redirect=0;
                                
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
                          
                            osw.close();

                        }catch(Exception e){
                            System.err.println(e); 
                        }
                    break;

            case "APACHE":
                    
                    if(secured){
                        
                        System.out.println("\n Safe HTTP Test:\n ");
                        
                        try{ 
                            
                            String cipher;
                            
                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cipher_file)));
                            
                            while ((cipher = br.readLine()) != null)   {
                                
                                String command="ab -d -k -c "+this.threadNumber+" -n "+accuracy+ " -f "+https_protocol+" -Z"+cipher+" "+site;
                                
                                String results=te.runCommand(command,true);

                                String[] lines = results.split(System.getProperty("line.separator"));
                                
                                if(lines.length<=30){

                                    System.out.println(cipher+" : "+" Error");

                                }else{
                                    System.out.println(cipher+" :");
                                    System.out.println("\t"+lines[8]);
                                    System.out.println("\t"+lines[9]);
                                    System.out.println("\t"+lines[10]);
                                    System.out.println("\t"+lines[11]);
                                    System.out.println(" ");
                                    System.out.println("\t"+lines[31]);
                                    System.out.println("\t"+lines[32]);
                                    System.out.println(" ");
                                    
                                }
                                

                            }
                            br.close();

                        }catch(Exception e){

                            System.err.println("An error occurred due to reading the cipher file!");
                        
                        }
                        
                    }else{

                        System.out.println("\n UnSafe HTTP Test:\n ");
                            
                        String command="ab -d -k -c "+this.threadNumber+" -n "+accuracy+ " "+site;
                        
                        String results=te.runCommand(command,true);
                        
                        String[] lines = results.split(System.getProperty("line.separator"));
                    
                        if(lines.length<=30){
                            
                            System.out.println(">>"+" Error");
                            
                        }else{
                            
                            System.out.println("\t"+lines[8]);
                            System.out.println("\t"+lines[9]);
                            System.out.println("\t"+lines[10]);
                            System.out.println("\t"+lines[11]);
                            System.out.println(" ");
                            // System.out.println("\t"+lines[31]);
                            // System.out.println("\t"+lines[32]);
                            // System.out.println("\t"+lines[33]);
                            // System.out.println("\t"+lines[34]);
                            System.out.println(" ");
                        } 

                    }
                    
                    break;    

              }
         
      }
   
     public void start ()
     {
        if (t == null){ t = new Thread (this, threadName); t.start (); }
     }

}