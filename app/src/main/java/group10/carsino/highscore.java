package group10.carsino;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import group10.R;

import android.view.Window;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import group10.db.JavaDBCon;
import group10.db.JavaDBCon.data;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class highscore extends ActionBarActivity {
    //Get's a list with name and score from the database in to the ListView with asyntask
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                data[] s = JavaDBCon.Getdatas();
                System.out.println("vad är detta " + s);

               final ArrayList<String> list = new ArrayList<>();

                for (int i = 0; i < s.length; i++) {
                    list.add(s[i].getName() + "\t" + "Score:" + s[i].getscore());
                    System.out.println(s[i].getName() + s[i].getscore());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateListView(list);

                    }
                });
                return null;
            }
        }.execute();
    }
    // set a listview
    public void populateListView(ArrayList list){
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.name_list, list));
       // arrayAdapter = new ArrayAdapter(this, android.R.layout.name_list, list);
        //listview.setAdapter(arrayAdapter);
    }
}





   /** class MyTask extends AsyncTask<Void,String,Void>{
        private ArrayAdapter<String> adapter;


        data[] Data = JavaDBCon.Getdatas();
        String[] teams = new String[Data.length];
        private int count=0;
        @Override
        protected void onPreExecute() {

            setProgressBarIndeterminate(false);
            setProgressBarVisibility(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String item:teams) {
                publishProgress(item);

                   try {
                       Thread.sleep(200);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            count++;
            setProgress((int) ((double) count / teams.length) * 10000);
        }

        @Override
        protected void onPostExecute(Void result) {
            setProgressBarVisibility(false);
            L.s(highscore.this,"sucess");


        }
    }*/





    /**public void populataListView() {
        data[] Data = JavaDBCon.Getdatas();
        final String[] listString = new String[Data.length];

        for (int i = 0; i < Data.length; i++) {
            listString[i] = Data[i].getName() + "\t" + "Score:" + Data[i].getscore();

            ListView list = (ListView) findViewById(R.id.listView);

            list.setAdapter(new ArrayAdapter<String>(this, R.layout.name_list, listString));

            //new MyTask().execute();

        }
    }
}*/
       /** class MyTask extends AsyncTask<Void, String, Void> {
            private ArrayAdapter<String> adapter;




            private int count = 0;

            @Override
            protected void onPreExecute() {
                adapter = (ArrayAdapter<String>) list.getAdapter();
                setProgressBarIndeterminate(false);
                setProgressBarVisibility(true);
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (String item : teams)
                    publishProgress(item);


                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                adapter.add(values[0]);
                count++;
                setProgress((int) ((double) count / teams.length) * 10000);
            }

            @Override
            protected void onPostExecute(Void result) {
                setProgressBarVisibility(false);
                L.s(highscore.this, "sucess");


            }
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}*/