package controller_view;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.LinkedList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * This class manages all music and sound effects being played for pokemon.
 * It uses methods such as playSound or playSong that takes a string parameter 
 * to find the correct music/sound file within the folder. Then proceeds to call
 * the playing method that would actually create the media player and play it.
 * All song names and sound names are held inside a LinkedList so that it is 
 * serializable and can be used later to hold persistence.
 *
 */
public class MediaTrack implements Serializable {
	private static MediaPlayer player;
	private static MediaPlayer player2;
	private LinkedList<String> songs;
	private LinkedList<String> sounds;
	private String currentSong;
	private String currentSound;
	
	public MediaTrack() {
		//Hard coded songs
		songs = new LinkedList<String>();
		songs.add("BattleCaughtPokemon");
		songs.add("BattleEncounter");
		songs.add("Cave");
		songs.add("ItemPickup");
		songs.add("SafariZone");
		songs.add("TitleScreen");
		songs.add("Town");
		
		//Hard code sounds
		sounds = new LinkedList<String>();
		sounds.add("select");
		sounds.add("menu_open");
		sounds.add("dooropen");
		sounds.add("enter");
	}
	
	private void playAnMP3() {
		// Need a File and URI object so the path works on all OSs
		File file = new File("music/"+currentSong+".mp3");
		URI uri = file.toURI();
	    // Play one mp3 and and have code run when the song ends
		Media media = new Media(uri.toString());
		player = new MediaPlayer(media);
		player.setOnEndOfMedia(new EndOfSongHandler());
		player.setVolume(0.2);
		player.play();
		
	}
	
	private void playASound() {
		// Need a File and URI object so the path works on all OSs
		File file = new File("music/SoundEffects/"+currentSound+".wav");
		URI uri = file.toURI();
	    // Play one mp3 and and have code run when the song ends
		Media media = new Media(uri.toString());
		player2 = new MediaPlayer(media);
		player2.setVolume(0.5);
		player2.setRate(1.5);
		player2.play();
		
	}
	public void stop() {
		player.stop();
	}
	
	public void playSong(String song) {
		for (String s: songs) {
			if (s.equals(song)) {
				currentSong = s;
				playAnMP3();
			}
		}
	}
	
	public void playSound(String sound) {
		for (String s: sounds) {
			if (s.equals(sound)) {
				currentSound = s;
				playASound();
			}
		}
	}
	
	/**
	 * Handler for when the song ends, in this case, it would just dequeue the song in the list,
	 * refreshes the list view, and calling playAnMP3 again if there is still any song within the playlist
	 *
	 */
	private class EndOfSongHandler implements Runnable {
    @Override
    public void run() {
    	playAnMP3();
    }
  }
}
