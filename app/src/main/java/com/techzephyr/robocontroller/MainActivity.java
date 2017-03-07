package com.techzephyr.robocontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String[] DrawerListitems={"Help", "TZ Crew", "Info", "Exit"};
	private Button on,off,connect;
	private Button fwd, bck, lft, rgt, stop;
	private DrawerLayout DL;
	private ActionBarDrawerToggle ABDT;
	public BluetoothAdapter BA;
	private ListView lv;
	private ScrollView scroll;
	private TextView statusdesc, incomingdata;
	private ArrayAdapter<String> BTArrayAdapter;
	public BluetoothSocket bsckt;
	public BluetoothDevice device;
	public OutputStream opstream;
	public InputStream ipstream;
	protected static final int MESSAGE_READ = 0;
	private static final int REQUEST_CONNECT_DEVICE = 1;
	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard Serial Port Service ID
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case MESSAGE_READ:
				byte[] readBuf = (byte[])msg.obj;
				String string = new String(readBuf);
				incomingdata.append(string);
				scroll.fullScroll(View.FOCUS_DOWN);
				break;
			}
		}
	};
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      BA = BluetoothAdapter.getDefaultAdapter();
	      lv = (ListView)findViewById(R.id.listView1); //drawer list view
	      lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, DrawerListitems));
		  lv.setOnItemClickListener(new DrawerItemClickListener());
	      DL = (DrawerLayout)findViewById(R.id.drawer_layout);
	      //getActionBar().setDisplayHomeAsUpEnabled(true);
	      //getActionBar().setHomeButtonEnabled(true);
	      ABDT = new ActionBarDrawerToggle(this, DL, R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close)
			{
		            public void onDrawerClosed(View view) {
		                getActionBar().setTitle(getTitle());
		                invalidateOptionsMenu();
		            }	           
		            public void onDrawerOpened(View drawerView) {
		                getActionBar().setTitle(getTitle());
		                invalidateOptionsMenu();
		            }
		        };
		        DL.setDrawerListener(ABDT);
		       // getActionBar().setDisplayHomeAsUpEnabled(true);
		       // getActionBar().setHomeButtonEnabled(true);
	      on = (Button)findViewById(R.id.on);
	    	    
	      off = (Button)findViewById(R.id.close);
	     	      
	      connect = (Button)findViewById(R.id.connect);
	      connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 find(v);
			}
		});
			
	      fwd = (Button)findViewById(R.id.fwd);
	      		fwd.setEnabled(false);
	      		fwd.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction() == MotionEvent.ACTION_MOVE){
							try {
								fwd(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(event.getAction() == MotionEvent.ACTION_UP){
							try {
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return false;
					}
				});
			
	      lft = (Button)findViewById(R.id.lft);
				lft.setEnabled(false);
				lft.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction() == MotionEvent.ACTION_MOVE){
							try {
								lft(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(event.getAction() == MotionEvent.ACTION_UP){
							try {
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return false;
					}
				});
			
	      stop = (Button)findViewById(R.id.stp);
				stop.setEnabled(false);
				stop.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction() == MotionEvent.ACTION_MOVE){
							try {
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(event.getAction() == MotionEvent.ACTION_UP){
							try {
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return false;
					}
				});
	      rgt = (Button)findViewById(R.id.rgt);
				rgt.setEnabled(false);
				rgt.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction() == MotionEvent.ACTION_MOVE){
							try {
								rgt(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(event.getAction() == MotionEvent.ACTION_UP){
							try {
								//rgt.setBackgroundColor(Color.parseColor("#F55353"));
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return false;
					}
				});
						
	      bck = (Button)findViewById(R.id.bck);
				bck.setEnabled(false);
				bck.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if(event.getAction() == MotionEvent.ACTION_MOVE){
							try {
								bck(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(event.getAction() == MotionEvent.ACTION_UP){
							try {
								stp(v);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return false;
					}
				});
	      statusdesc = (TextView)findViewById(R.id.statusdesc);
	      statusdesc.setText("Bluetooth not Connected");
	      statusdesc.setTextColor(Color.parseColor("#33B5E5"));
	      scroll = (ScrollView)findViewById(R.id.scroller);
	      incomingdata = (TextView)findViewById(R.id.incomingdata);
	      incomingdata.setTextColor(Color.parseColor("#33B5E5"));
	      incomingdata.setMovementMethod(new ScrollingMovementMethod());
	   }
	   @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   // TODO Auto-generated method stub
		   super.onActivityResult(requestCode, resultCode, data);
		   if(requestCode==REQUEST_CONNECT_DEVICE){
			   if(resultCode == RESULT_OK){
				// Get the device MAC address
	                String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
	                // Get the BLuetoothDevice object
	                BluetoothDevice device = BA.getRemoteDevice(address);
	                try {
						openBT(device);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }
		   }
	   }
	   public void on(View view){
	      if (!BA.isEnabled()) {
	         Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	         startActivityForResult(turnOn, 0);
	      }
	      else{
	         Toast.makeText(getApplicationContext(),"Already on",Toast.LENGTH_LONG).show();
	         }
	   }
	  
		public void find(View view) {
			
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		   }
		 
	   public void close(View view){
		   	if(bsckt!= null){
		   		try {
				   bsckt.close();
				   
				   fwd.setEnabled(false);
				   lft.setEnabled(false);
				   rgt.setEnabled(false);
				   stop.setEnabled(false);
				   stop.setText("OFF");
				   bck.setEnabled(false);
			   	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   	}
		 }
		  
		   
		   void openBT(BluetoothDevice device) throws IOException
		    {
		        
		        try{
		        	bsckt = device.createRfcommSocketToServiceRecord(uuid);
		        	
		        	bsckt.connect();
		        	statusdesc.setText("Bluetooth Connection Established");
		        	stop.setText("ON");
		        	fwd.setEnabled(true);
		        	lft.setEnabled(true);
		        	rgt.setEnabled(true);
		        	stop.setEnabled(false);
		        	bck.setEnabled(true);
		        }
		        catch(IOException e)
		        {
		        	e.printStackTrace();
		        }
		        opstream = bsckt.getOutputStream(); //for transmission of signals over BT
		        ipstream = bsckt.getInputStream(); //for receiving of signals over BT
		        ConnectedThread r = new ConnectedThread();
		        r.start();
		    }
		   public class DrawerItemClickListener implements ListView.OnItemClickListener{

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					switch(position){
					case 0: 
						Intent i = new Intent(MainActivity.this,HelpActivity.class);
						startActivity(i);
						DL.closeDrawer(lv);
						break;
					case 1:
						Intent i1 = new Intent(MainActivity.this,Tzmembers.class);
						startActivity(i1);
						DL.closeDrawer(lv);
						break;
					case 2:Intent i2 = new Intent(MainActivity.this,Info.class);
					startActivity(i2);
					DL.closeDrawer(lv);
							break;
					case 3:
						finish();
					}
				}   
		   }
		   public void sendData(String string) {
			   try {
				bsckt.getOutputStream().write(string.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    }
			public void fwd(View v) throws IOException{
				
				sendData("w");
				statusdesc.setText("Forward");
			}
			public void lft(View v) throws IOException{
				sendData("a");
				statusdesc.setText("Left");
			}
			public void stp(View v) throws IOException{
				
				sendData("x");
				statusdesc.setText("Stop");
			}
			public void rgt(View v) throws IOException{
				sendData("d");
				statusdesc.setText("Right");
			}
			public void bck(View v) throws IOException{
				sendData("s");
				statusdesc.setText("Back");
			}
			//Below code snippet is taken from android developer site
			private class ConnectedThread extends Thread {

				  	private final InputStream mmInStream;
				 
				    public ConnectedThread() {
				        InputStream tmpIn = null;
				 
				        // Get the input and output streams, using temp objects because
				        // member streams are final
				        try {
				            tmpIn = bsckt.getInputStream();
				        } catch (IOException e) { }
				 
				        mmInStream = tmpIn;
				    }
				 
				    public void run() {
				    	byte[] buffer;
				    	int bytes;
				         // bytes returned from read()

				        // Keep listening to the InputStream until an exception occurs
				        while (true) {
				        	buffer = new byte[256];  // buffer store for the stream
					        
				        	try {
				                // Read from the InputStream
				                bytes = mmInStream.read(buffer);
				                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
				            } catch (IOException e) {
				                break;
				            }
				        }
				    }
				}
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   @Override
		protected void onPostCreate(Bundle savedInstanceState) {
			super.onPostCreate(savedInstanceState);
			// Sync the toggle state after onRestoreInstanceState has occurred.
			ABDT.syncState();
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {
			super.onConfigurationChanged(newConfig);
			// Pass any configuration change to the drawer toggls
			ABDT.onConfigurationChanged(newConfig);
		}
	   @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
