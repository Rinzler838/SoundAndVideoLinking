package activity.ctec.soundandvideolinking;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SoundActivity extends Activity implements Runnable
{
    private Button pausePlayButton;
    private Button stopButton;
    private Button videoButton;
    private Button nextButton;
    private MediaPlayer soundPlayerOne;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private String pause;
    private String play;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        pausePlayButton = (Button) findViewById(R.id.pausePlayButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundPlayerOne = MediaPlayer.create(this.getBaseContext(), R.raw.tsfh);
        pause = getString(R.string.pause);
        play = getString(R.string.play);


        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }

    private void setupListeners()
    {

        pausePlayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (pausePlayButton.getText().toString().equals(play))
                {
                    soundPlayerOne.start();
                    pausePlayButton.setText("PAUSE");
                }

                else
                {
                    soundPlayerOne.pause();
                    pausePlayButton.setText(play);
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                soundPlayerOne.stop();
                soundPlayerOne = MediaPlayer.create(getBaseContext(), R.raw.tsfh);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), SoundActivityTwo.class);
                startActivityForResult(myIntent, 0);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
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
                    soundPlayerOne.seekTo(progress);
                }
            }
        });
    }

    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal = soundPlayerOne.getDuration();
        soundSeekBar.setMax(soundTotal);

        while (soundPlayerOne != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayerOne.getCurrentPosition();
            }
            catch (InterruptedException soundException)
            {
                return;
            }
            catch (Exception otherException)
            {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
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
