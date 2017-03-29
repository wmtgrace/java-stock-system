
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */
public final class Music {
    private AudioClip click;
    public Music(String musicName) {
        URL urlClick = Music.class.getResource(musicName);
        click = Applet.newAudioClip(urlClick);
        
    }
    public boolean musicstop(boolean inMusic){
            if(inMusic){
                click.stop();
                return false;
            }
            else{
                click.loop();
                return true;
            }
        
    }
    public void musicplay(){
        click.play();
    }
}
