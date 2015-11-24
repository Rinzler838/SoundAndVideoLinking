package activity.ctec.soundandvideolinking;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SoundActivityTwo extends Activity implements Runnable
{
    private Button pausePlayButtonTwo;
    private Button stopButtonTwo;
    private Button videoButtonTwo;
    private Button backButtonTwo;
    private MediaPlayer soundPlayerTwo;
    private SeekBar soundSeekBarTwo;
    private Thread soundThread;
    private String pause;
    private String play;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_activity_two);

        pausePlayButtonTwo = (Button) findViewById(R.id.pausePlayButton);
        stopButtonTwo = (Button) findViewById(R.id.stopButton);
        backButtonTwo = (Button) findViewById(R.id.backButtonTwo);
        soundSeekBarTwo = (SeekBar) findViewById(R.id.soundSeekBar);
        videoButtonTwo = (Button) findViewById(R.id.videoButton);
        soundPlayerTwo = MediaPlayer.create(this.getBaseContext(), R.raw.tsfhtwo);
        pause = getString(R.string.pause);
        play = getString(R.string.play);

        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }

    private void setupListeners()
    {

        pausePlayButtonTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (pausePlayButtonTwo.getText().toString().equals(play))
                {
                    soundPlayerTwo.start();
                    pausePlayButtonTwo.setText(pause);
                } else
                {
                    soundPlayerTwo.pause();
                    pausePlayButtonTwo.setText(play);
                }

            }
        });

        stopButtonTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                soundPlayerTwo.stop();
                soundPlayerTwo = MediaPlayer.create(getBaseContext(), R.raw.tsfhtwo);
            }
        });

        backButtonTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), SoundActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });


        videoButtonTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        soundSeekBarTwo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    soundPlayerTwo.seekTo(progress);
                }
            }
        });
    }

    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal = soundPlayerTwo.getDuration();
        soundSeekBarTwo.setMax(soundTotal);

        while (soundPlayerTwo != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayerTwo.getCurrentPosition();
            }
            catch (InterruptedException soundException)
            {
                return;
            }
            catch (Exception otherException)
            {
                return;
            }
            soundSeekBarTwo.setProgress(currentPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound_activity_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
