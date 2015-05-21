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
















public class highscore extends ActionBarActivity {
    ListView list;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_highscore);


        populataListView();





    }
    class MyTask extends AsyncTask<Void,String,Void>{
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
    }





    public void populataListView() {
        data[] Data = JavaDBCon.Getdatas();
        final String[] teams = new String[Data.length];

        for (int i = 0; i < Data.length; i++) {
            teams[i] = Data[i].getName() + "\t" +"Score:"+ Data[i].getscore();


            ListView list = (ListView) findViewById(R.id.listView);

            list.setAdapter(new ArrayAdapter<String>(this, R.layout.name_list, teams));

            new MyTask().execute();

        }
        class MyTask extends AsyncTask<Void, String, Void> {
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



}