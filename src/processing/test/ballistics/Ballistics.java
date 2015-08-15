package processing.test.ballistics;

import processing.core.*;
import processing.test.ballistics.R;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.media.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Random;


public class Ballistics extends PAppletlol {

	public int posX;// = width/3;
	public int posY;// = 250;
	public float pong1Center = width / 2;
	public float pong2Center = width / 2;
	public float pong1Extra = width / 2;
	public float pong2Extra = width / 2;
	public boolean wantMusic = true;
	public boolean wantSounds = true;
	public boolean wantVoice = true;
	public float pongStrokeWeight;
	public float strokeWeightValue;
	public Random random = new Random();
	public int speedX = random.nextInt(4) + 3;
	public int speedY = random.nextInt(4) + 3;
	public int score0 = 0;
	public int score = 0;
	public int score2 = 0;
	public int gameMode = -1;
	public int gameMode2;
	public int ballRadius;// = 25;
	public int lineHeight;// = 150;
	public int pongHeight;// = 5;
	public int pongRadius;// = 85;
	public int ellipseR = 255;
	public int ellipseG = 255;
	public int ellipseB = 255;
	public int currenthighscore;
	protected int backgroundG = 255;
	public SharedPreferences highscore;
	public MediaPlayer jeahao;
	//public MediaPlayer dhruvVoice;
	public MediaPlayer menuMusic;
	public MediaPlayer deathMusic;
	public MediaPlayer hitWall;
	public MediaPlayer hitPong;
	public MediaPlayer levelUp;
	public float sw, sh, touchX, touchY;
	public int pointerCount;
	public ArrayList<PVector> points;
	public PVector tempPoint;
	public PVector tempPointA;
	public PVector tempPointB;
	public PVector tempPoiint;
	public ArrayList<PVector> tempPoint1 = new ArrayList<PVector>();
	public ArrayList<PVector> tempPoint2 = new ArrayList<PVector>();
	public ArrayList<PVector> tempPoint3 = new ArrayList<PVector>();
	public ArrayList<PVector> tempPoint4 = new ArrayList<PVector>();
	public ArrayList<PVector> aTempPoint = new ArrayList<PVector>();
	public ArrayList<PVector> bTempPoint = new ArrayList<PVector>();
	public boolean bottomIsWinner = false;
	public boolean topIsWinner = false;
	public String message;
	PFont orbitronNormal;
	PFont orbitronBold;
	Editor edit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		posX = width / 2;
		posY = height / 2;
		ballRadius = width / 28;
		lineHeight = height / 8;
		pongHeight = height / 256;
		pongRadius = width / 8;
		super.onCreate(savedInstanceState);
		background(255);
		textAlign(CENTER, CENTER);
		textSize(30);
		jeahao = MediaPlayer.create(this, R.raw.jeaahaooloop);
		jeahao.setVolume((float) 0.0, (float) 0.7);
		menuMusic = MediaPlayer.create(this, R.raw.menutheme);
		menuMusic.setLooping(true);
		deathMusic = MediaPlayer.create(this, R.raw.gameover);
		deathMusic.setLooping(false);
		//dhruvVoice = MediaPlayer.create(this, R.raw.dhruvvoice);
		hitPong = MediaPlayer.create(this, R.raw.hitpong);
		hitPong.setLooping(false);
		hitWall = MediaPlayer.create(this, R.raw.hitwall);
		hitWall.setLooping(false);
		levelUp = MediaPlayer.create(this, R.raw.levelup);
		levelUp.setLooping(false);
		points = new ArrayList<PVector>();
		PVector lol = new PVector(0, 0);
		tempPoint3.add(lol);
		tempPoint4.add(lol);
		pongStrokeWeight = (float) height / (1280 / 3);
		orbitronNormal = createFont("Orbitron.ttf", 150);
		orbitronBold = createFont("Orbitron-Bold.ttf", 150);
		highscore = this.getSharedPreferences("userdetails", MODE_PRIVATE);
		edit = highscore.edit();
		currenthighscore = highscore.getInt("highscore", 0);
		strokeWeightValue = (float) height / (1280 / 15);

	}

	private float getSize(float number) {
		return (720 / number);
	}

	public void draw() {
		/*
		 * Game Modes: -1 - starting screen 0 - One Player MOde 1- 1 Player
		 * death screen 2- Two Player Mode 3 - Two Player Pause/Death screen 4-
		 * Two Player mode selection 5- How to Play 6- Options
		 */

		if (gameMode == -1) {
			if (wantMusic){
				menuMusic.start();
			} else{
				menuMusic.pause();
			}
			//menuMusic.seekTo(0);
			Log.e("debug", speedX + ", " + speedY);
			textFont(orbitronBold);
			textAlign(CENTER, CENTER);
			background(0);
			ellipse(posX, posY, ballRadius, ballRadius);
			fill(0);
			strokeWeight(strokeWeightValue);
			stroke(255);
			rect(width / 16, height * 1 / 16, width * 7 / 8, height * 1 / 8);
			stroke(236, 0, 140);
			rect(width / 16, height * 4 / 16, width * 7 / 8, height * 1 / 8);
			stroke(0, 174, 239);
			rect(width / 16, height * 7 / 16, width * 7 / 8, height * 1 / 8);
			stroke(249, 237, 50);
			rect(width / 16, height * 10 / 16, width * 7 / 8, height * 1 / 8);
			stroke(57, 181, 74);
			rect(width / 16, height * 13 / 16, width * 7 / 8, height * 1 / 8);

			textSize(width / getSize(85));
			fill(255);
			text("BALLISTICS", width / 2, height * 2 / 16);
			fill(236, 0, 140);
			text("1 PLAYER", width / 2, height * 37 / 128);
			textFont(orbitronNormal);
			textSize(width / getSize(35));
			text("HIGHSCORE: " + currenthighscore, width / 2, height * 44 / 128);
			textFont(orbitronBold);
			textSize(width / getSize(85));
			fill(0, 174, 239);
			text("2 PLAYERS", width / 2, height * 8 / 16);
			fill(249, 237, 50);
			textSize(width / getSize(73));
			text("HOW TO PLAY", width / 2, height * 11 / 16);
			fill(57, 181, 74);
			textSize(width / getSize(85));
			text("OPTIONS", width / 2, height * 109 / 128);
			textFont(orbitronNormal);
			textSize(width / getSize(22));
			text("DISCLAIMER: 'WIN THE GAME' IS NOT AN OPTION", width / 2,
					height * 116 / 128);
			textFont(orbitronBold);
			textSize(width / getSize(85));

		}

		if (gameMode == 0) {
			
			strokeWeight(pongStrokeWeight);
			background(0, 0, 0);
			stroke(255);
			line(0, height - lineHeight, width, height - lineHeight);
			fill(ellipseR, ellipseG, ellipseB);
			stroke(ellipseR, ellipseG, ellipseB);
			ellipse(posX, posY, ballRadius, ballRadius);
			posX = posX + speedX;
			posY = posY + speedY;

			rectMode(CENTER);
			noFill();
			rect(mouseX, height - lineHeight, pongRadius * 2, pongHeight * 2);
			fill(255);
			textSize(width / getSize(150));
			text(score0, width / 2, height / 6);

			// Bounce off right
			if (posX + ballRadius > width && posY - ballRadius > 0) {
				speedX = speedX * -1;
				if (wantSounds) {
					hitWall.start();
					hitWall.seekTo(0);
				}

			}

			// Bounce off top
			if (posY - ballRadius < 0 && posX + ballRadius < width
					&& posX - ballRadius > 0) {
				speedY = speedY * -1;
				if (wantSounds) {
					hitWall.start();
					hitWall.seekTo(0);
				}
			}

			// Bounce off left
			if (posX - ballRadius <= 0
					//&& posY + ballRadius < height - lineHeight
					&& posY - ballRadius > 0) {
				speedX = speedX * -1;
				if (wantSounds) {
					hitWall.start();
					hitWall.seekTo(0);
				}
			}

			// Bounce off top right corner
			if (posX + ballRadius > width && posY - ballRadius < 0) {
				speedX = speedX * -1;
				speedY = speedY * -1;
				if (wantSounds) {
					hitWall.start();
					hitWall.seekTo(0);
				}
			}

			// Bounce off top left corner
			if (posX - ballRadius < 0 && posY - ballRadius < 0) {
				speedX = speedX * -1;
				speedY = speedY * -1;
				// hitWall.pause();
				if (wantSounds) {

					hitWall.start();
					hitWall.seekTo(0);
				}
			}

			// Bounce off pad
			if (posX + ballRadius > mouseX - pongRadius
					&& posX - ballRadius < mouseX + pongRadius
					&& posY + ballRadius > height - lineHeight - pongHeight) {
				score0 = score0 + 1;

				if (wantSounds) {
					hitPong.start();
					hitPong.seekTo(0);

					//
				}
				speedY = speedY * -1;

				if (speedX < 0) {
					speedX = speedX - 5;
				} else {
					speedX = speedX + 5;
				}
				speedY = speedY - 5;
			
				
			


				if (score0 < 5) {
				}

				if (score0 == 5) {
					if (wantSounds) {
						levelUp.start();
						levelUp.seekTo(0);
					}
					ellipseR = 255;
					ellipseG = 0;
					ellipseB = 0;
					speedX = speedX/3;
					speedY = speedY/3;
					pongRadius = pongRadius - width / 72;
				}

				if (score0 == 10) {
					if (wantSounds) {
						levelUp.start();
						levelUp.seekTo(0);
					}
					ellipseG = 255;
					ellipseR = 0;
					ellipseB = 0;
					speedX = speedX/3;
					speedY = speedY/3;
					pongRadius = pongRadius - width / 72;
				}
				if (score0 == 15) {
					if (wantSounds) {
						levelUp.start();
						levelUp.seekTo(0);
					}
					ellipseG = 0;
					ellipseB = 255;
					ellipseR = 255;
					speedX = speedX/3;
					speedY = speedY/3;
					pongRadius = pongRadius - width / 72;
				}
			}
				
				
			else if (posX + ballRadius < mouseX - pongRadius
					&& posY + ballRadius > height - pongHeight - lineHeight) {

				if (wantMusic) {
					deathMusic.setLooping(false);
					deathMusic.start();
					deathMusic.seekTo(0);
				}
				jeahao.pause();
				if (score0 > currenthighscore) {
					currenthighscore = score0;
					edit.putInt("highscore", currenthighscore);
					edit.commit();
				}
				gameMode = 1;
			}

			else if (posX - ballRadius > mouseX + pongRadius
					&& posY + ballRadius > height - pongHeight - lineHeight) {
				if (wantMusic) {
					deathMusic.setLooping(false);
					deathMusic.start();
					deathMusic.seekTo(0);
				}
				jeahao.pause();
				if (score0 > currenthighscore) {
					currenthighscore = score0;
					edit.putInt("highscore", currenthighscore);
					edit.commit();
				}
				gameMode = 1;
			}
		}

		if (gameMode == 1) {
			textFont(orbitronBold);
			background(0, 0, 0);
			rectMode(CORNER);
			fill(0);
			stroke(255);
			strokeWeight(strokeWeightValue);
			rect(width / 16, height * 2 / 16, width * 7 / 8, height * 3 / 16);
			rect(width / 16, height * 10 / 16, width * 7 / 8, height * 1 / 8);
			rect(width / 16, height * 13 / 16, width * 7 / 8, height * 1 / 8);

			fill(255);
			textSize(width / getSize(75));
			text("HIGHSCORE: " + currenthighscore, width / 2, height * 1 / 16);
			textSize(width / getSize(200));
			text(score0, width / 2, height * 7 / 32);

			if (score0 == 0) {
				textSize(width / getSize(100));
				message = "BAD DAY, HUH?";
			}
			if (score0 > 0 && score0 < 5) {
				textSize(width / getSize(50));
				message = "IF THIS IS YOUR BEST, YOUR LIFE WILL ALWAYS BE IN BLACK AND WHITE.";
			}
			if (score0 > 4 && score0 < 10) {
				textSize(width / getSize(40));
				message = "JUST AS YOU FEEL A SENSE OF ACHIEVEMENT, YOU REALISE THAT YOUR SCORE IS STILL LOWER THAN YOUR AGE.";
			}
			if (score0 > 9 && score0 < 15) {
				textSize(width / getSize(60));
				message = "LOOKS LIKE YOUR SYNAPSES ARE FORMING.";
			}
			if (score0 > 14 && score0 < 20) {
				textSize(width / getSize(60));
				message = "LOOKS LIKE YOUR SYNAPSES ARE FORMING.";
			}

			if (score0 > 19 && score0 < 25) {
				textSize(width / getSize(70));
				message = "NO, IT DOESN'T SLOW DOWN.";
			}
			if (score0 > 24) {
				textSize(width / getSize(45));
				message = "THE CREATERS WOULD LIKE TO KNOW MORE ABOUT THIS GLITCH YOU HAVE EXPLOITED.";
			}

			text(message, width * 1 / 16, height * 11 / 32, width * 7 / 8,
					height * 4 / 16);
			textSize(width / getSize(115));
			text("AGAIN", width / 2, height * 11 / 16);
			text("ENOUGH", width / 2, height * 14 / 16);

		}

		if (gameMode == 2) {
			// TwoPlayer two = new TwoPlayer();
			// two.play();
			twoPlayerMode();
			// TODO Auto-generated method stub
		}

		if (gameMode == 3) {
			background(0, 0, 0);
			rectMode(CORNER);
			fill(0, 0, 0);
			stroke(255, 255, 255);
			strokeWeight(strokeWeightValue);
			rect(width / 16, height * 11 / 32, width * 14 / 16, height * 9 / 64);
			rect(width / 16, height * 33 / 64, width * 14 / 16, height * 9 / 64);

			if (!bottomIsWinner && !topIsWinner) {
				fill(255);
				textSize(250);
				text(score2, width / 2, height / 8);
				text(score, width / 2, 7 * height / 8);
				textSize(width / getSize(100));
				text("RESUME", width / 2, height * 53 / 128);
				text("ENOUGH", width / 2, height * 75 / 128);

			} else {

				fill(255, 255, 255);
				textSize(width / getSize(100));
				text("AGAIN", width / 2, height * 53 / 128);
				text("ENOUGH", width / 2, height * 75 / 128);

				textSize(width / getSize(150));
				if (bottomIsWinner) {

					text("THUG", width / 2, height * 13 / 16);
					textSize(width / getSize(25));
					text("DISCLAIMER: YOUR VICTORY DOES NOT REPRESENT YOUR SUCCESS IN LIFE",
							width * 1 / 8, height * 14 / 16, width * 6 / 8,
							height * 2 / 16);

					pushMatrix();
					translate(width / 2, height * 3 / 16);
					rotate(PI);
					textSize(width / getSize(150));
					text("NOOB", 0, 0);
					popMatrix();

					pushMatrix();
					textSize(width / getSize(25));
					translate(width * 3 / 32, 0);
					rotate(PI);
					text("FACT: MOST NOOBS WILL BE TOO BUSY ABUSING THE 'AGAIN' BUTTON TO NOTICE THIS MESSAGE",
							0, 0, width * -26 / 32, height * -2 / 16);
					popMatrix();

				} else {
					text("NOOB", width / 2, height * 13 / 16);
					textSize(width / getSize(25));
					text("FACT: MOST NOOBS WILL BE TOO BUSY ABUSING THE 'AGAIN' BUTTON TO NOTICE THIS MESSAGE",
							width * 1 / 8, height * 14 / 16, width * 6 / 8,
							height * 2 / 16);

					pushMatrix();
					translate(width / 2, height * 3 / 16);
					rotate(PI);
					textSize(width / getSize(150));
					text("THUG", 0, 0);
					popMatrix();

					pushMatrix();
					textSize(width / getSize(25));
					translate(width * 3 / 32, 0);
					rotate(PI);
					text("DISCLAIMER: YOUR VICTORY DOES NOT REPRESENT YOUR SUCCESS IN LIFE",
							0, 0, width * -26 / 32, height * -2 / 16);
					popMatrix();
				}

			}

		}
		if (gameMode == 4) {
			background(0);
			fill(0);
			strokeWeight(strokeWeightValue);
			textFont(orbitronBold);
			stroke(237, 28, 36);
			rect(width * 1 / 16, height * 1 / 13, width * 14 / 16,
					height * 2 / 13);
			stroke(247, 148, 30);
			rect(width * 1 / 16, height * 4 / 13, width * 14 / 16,
					height * 2 / 13);
			stroke(255, 242, 0);
			rect(width * 1 / 16, height * 7 / 13, width * 14 / 16,
					height * 2 / 13);
			stroke(255, 255, 255);
			rect(width * 1 / 16, height * 10 / 13, width * 14 / 16,
					height * 2 / 13);
			textSize(width / getSize(64));
			fill(237, 28, 36);
			text("SUDDEN DEATH", width / 2, height * 2 / 13);
			fill(247, 148, 30);
			textSize(width / getSize(95));
			text("BEST OF 3", width / 2, height * 5 / 13);
			fill(255, 242, 0);
			text("BEST OF 5", width / 2, height * 8 / 13);
			fill(255, 255, 255);
			text("BACK", width / 2, height * 11 / 13);

		}

		if (gameMode == 5) {
			background(0);
			textFont(orbitronBold);
			textSize(width / getSize(70));
			fill(255);
			text("SOMETIMES IN LIFE, YOU HAVE TO LEARN FROM EXPERIENCE.",
					width * 1 / 8, height * 1 / 4, width * 6 / 8,
					height * 2 / 4);

		}

		if (gameMode == 6) {
			textFont(orbitronBold);
			textAlign(CENTER, CENTER);

			background(0);

			fill(0);
			strokeWeight(strokeWeightValue);
			stroke(255);
			rect(width / 16, height * 1 / 16, width * 7 / 8, height * 1 / 8);
			if (wantMusic){
				stroke(57, 181, 74);
			} else {
				stroke(237, 28, 36); 
			}
			rect(width / 16, height * 4 / 16, width * 7 / 8, height * 1 / 8);
			if (wantSounds){
				stroke(57, 181, 74);
			} else {
				stroke(237, 28, 36); 
			}
			rect(width / 16, height * 7 / 16, width * 7 / 8, height * 1 / 8);
			stroke(255, 255, 255);
			rect(width / 16, height * 10 / 16, width * 7 / 8, height * 1 / 8);
			stroke(255, 255, 255);
			rect(width / 16, height * 13 / 16, width * 7 / 8, height * 1 / 8);

			textSize(width / getSize(95));
			fill(255);
			text("OPTIONS", width / 2, height * 2 / 16);
			
			if (wantMusic) {
				fill(57, 181, 74);
				text("music: on", width / 2, height * 5 / 16);
			} else {
				fill(237, 28, 36);
				text("music: off", width / 2, height * 5 / 16);
			}
			
			if (wantSounds) {
				fill(57, 181, 74);
				text("sounds: on", width / 2, height * 8 / 16);
			} else {
				fill(237, 28, 36);
				text("sounds: off", width / 2, height * 8 / 16);
			}
			fill(255, 255, 255);
			textSize(width / getSize(65));
			text("reset highscore", width / 2, height * 85 / 128);
			textFont(orbitronNormal);
			textSize(width / getSize(22));
			text("current highscore: " + currenthighscore, width / 2,
					height * 92 / 128);
			textFont(orbitronBold);
			textSize(width / getSize(100));
			text("BACK", width / 2, height * 14 / 16);
		}

	}

	private void twoPlayerMode() {
		background(0, 0, 0);
		fill(255, 255, 255);
		stroke(255, 255, 255);
		strokeWeight(pongStrokeWeight);
		line(0, height - lineHeight, width, height - lineHeight);
		line(0, lineHeight, width, lineHeight);
		ellipse(posX, posY, ballRadius, ballRadius);
		posX = posX + speedX;
		posY = posY + speedY;
		noFill();
		rectMode(CENTER);
		if (pong2Center == 0) {
			pong2Center = width / 2;
		}
		if (pong1Center == 0) {
			pong1Center = width / 2;
		}
		if (pong2Extra == 0) {
			pong2Extra = width / 2;
		}
		if (pong1Extra == 0) {
			pong1Extra = width / 2;
		}
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i)!=null){
			tempPoint = (PVector) points.get(i);
			}
			if (speedY > 0) {
				rect(pong2Center, height - lineHeight, pongRadius * 2,
						pongHeight * 2);
				rect(pong1Extra, lineHeight, pongRadius * 2, pongHeight * 2);
			}

			if (speedY < 0) {
				rect(pong1Center, lineHeight, pongRadius * 2, pongHeight * 2);
				rect(pong2Extra, height - lineHeight, pongRadius * 2,
						pongHeight * 2);

			}
		}

		// If there are no points leave the paddles on the screen
		if (points.size() < 1) {
			if (speedY > 0) {
				rect(pong2Center, height - lineHeight, pongRadius * 2,
						pongHeight * 2);
				rect(pong1Extra, lineHeight, pongRadius * 2, pongHeight * 2);
			}
			if (speedY < 0) {
				rect(pong2Extra, height - lineHeight, pongRadius * 2,
						pongHeight * 2);
				rect(pong1Center, lineHeight, pongRadius * 2, pongHeight * 2);
			}

		}

		// }
		textSize(width / getSize(40));
		text(score2, width / 2, height / 6);
		text(score, width / 2, 5 * height / 6);

		// Bounce off right
		if (posX + ballRadius > width){
				//&& posY + ballRadius < height - lineHeight
				//&& posY - ballRadius > 0) {
			speedX = speedX * -1;
			if (wantSounds) {
				hitWall.start();
				hitWall.seekTo(0);
			}
		}

		// Bounce off top pad
		if (posX + ballRadius > pong1Center - pongRadius
				&& posX - ballRadius < pong1Center + pongRadius
				&& posY - ballRadius < lineHeight + pongHeight) {
			// score2 = score2+1;
			speedY = speedY * -1;
			if (wantSounds) {
				hitPong.start();
				hitPong.seekTo(0);
			}
			if (speedX < 0) {
				speedX = speedX - 2;
			} else {
				speedX = speedX + 2;
			}
			speedY = speedY + 2;
		

		}

		else if (posX + ballRadius < pong1Center - pongRadius
				&& posY - ballRadius < pongHeight + lineHeight) {
			if (wantMusic) {
				deathMusic.setLooping(false);
				deathMusic.start();
				deathMusic.seekTo(0);
			}
			jeahao.pause();
			score = score + 1;
			checkGameMode2();
			gameMode = 3;

		}

		else if (posX - ballRadius > pong1Center + pongRadius
				&& posY - ballRadius < pongHeight + lineHeight) {
			if (wantMusic) {
				deathMusic.setLooping(false);
				deathMusic.start();
				deathMusic.seekTo(0);
			}
			jeahao.pause();
			score = score + 1;
			checkGameMode2();
			gameMode = 3;
		}

		// Bounce off left
		if (posX - ballRadius <= 0 ){
				//&& posY < height - lineHeight
				//&& posY - ballRadius > 0) {
			speedX = speedX * -1;
			if (wantSounds) {
				hitWall.start();
				hitWall.seekTo(0);
			}
		}

		// Bounce off pad
		if (posX + ballRadius > pong2Center - pongRadius
				&& posX - ballRadius < pong2Center + pongRadius
				&& posY + ballRadius > height - lineHeight - pongHeight) {
			// score = score+1;

			speedY = speedY * -1;
			if (wantSounds) {
				hitPong.start();
				hitPong.seekTo(0);
			}
			if (speedX < 0) {
				speedX = speedX - 2;
			} else {
				speedX = speedX + 2;
			}
			speedY = speedY - 2;
		}

		else if (posX + ballRadius < pong2Center - pongRadius
				&& posY + ballRadius > height - pongHeight - lineHeight) {
			if (wantMusic) {
				deathMusic.setLooping(false);
				deathMusic.start();
				deathMusic.seekTo(0);
			}
			jeahao.pause();
			score2 = score2 + 1;
			checkGameMode2B();
			gameMode = 3;
		}

		else if (posX - ballRadius > pong2Center + pongRadius
				&& posY + ballRadius > height - pongHeight - lineHeight) {
			if (wantMusic) {
				deathMusic.setLooping(false);
				deathMusic.start();
				deathMusic.seekTo(0);
			}
			jeahao.pause();
			score2 = score2 + 1;

			checkGameMode2B();
			gameMode = 3;
		}

	}

	private void checkGameMode2B() {
		// TODO Auto-generated method stub
		if (gameMode2 == 21) {
			topIsWinner = true;
			// gameMode = 3;
		}
		if (gameMode2 == 22) {
			if (score2 == 2) {
				topIsWinner = true;
			}
			// gameMode = 3;
		}
		if (gameMode2 == 23) {
			if (score2 == 3) {
				topIsWinner = true;
			}
			// gameMode = 3;
		}
	}

	private void checkGameMode2() {
		// TODO Auto-generated method stub
		if (gameMode2 == 21) {
			bottomIsWinner = true;
			// gameMode = 3;
		}
		if (gameMode2 == 22) {
			if (score == 2) {
				bottomIsWinner = true;
			}
			// gameMode = 3;
		}
		if (gameMode2 == 23) {
			if (score == 3) {
				bottomIsWinner = true;
			}
			// gameMode = 3;
		}
	}

	public boolean surfaceTouchEvent(MotionEvent event) {
		if (gameMode == 2) {
			pointerCount = event.getPointerCount();

			int pointerIndex = event.getActionIndex();
			int pointerID = event.getPointerId(pointerIndex);
			points.clear();
			for (int i = 1; i <= pointerCount; i++) {

				points.add(new PVector(event.getX(i - 1), event.getY(i - 1)));
			}
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				detectTouchEvent(event);

			}

			if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
				for (int i = 0; i < pointerCount; ++i) {
					pointerIndex = i;
					pointerID = event.getPointerId(pointerIndex);
					if (pointerID == 0) {

						detectTouchEvent(event);
					}
					if (pointerID > 0) {
						if (speedY > 0) {
							if (event.getY(pointerIndex) > height / 2) {

								pong2Center = event.getX(pointerIndex);
								pong2Extra = pong2Center;
							}
							if (event.getY(pointerIndex) < height / 2) {

								pong1Extra = event.getX(pointerIndex);
								pong1Center = pong1Extra;
							}
						}
						if (speedY < 0) {
							if (event.getY(pointerIndex) > height / 2) {

								pong2Extra = event.getX(pointerIndex);
								pong2Center = pong2Extra;
							}
							if (event.getY(pointerIndex) < height / 2) {

								pong1Center = event.getX(pointerIndex);
								pong1Extra = pong1Center;
							}
						}
					}
				}
			}
			// if the event is a pressed gesture finishing,
			// it means the lifting the last touch point
			if (event.getActionMasked() == MotionEvent.ACTION_UP)
				points.clear();

			// if you want the variables for motionX/motionY, mouseX/mouseY etc.
			// to work properly, you'll need to call super.surfaceTouchEvent().
		}
		if (gameMode == -1) {
			// Clicked 1 Player
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 4 / 16
						&& event.getY() < height * 6 / 16) {
					if (wantMusic) {
						//menuMusic.pause();
						jeahao.setLooping(true);
						jeahao.start();
						jeahao.seekTo(0);
					}
					
					//posX = width-505;
					//posY = height - lineHeight - pongHeight - 500;
					speedX = random.nextInt(4) + 3;
					speedY = random.nextInt(4) + 3;
					gameMode = 0;
					menuMusic.pause();

				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 7 / 16
						&& event.getY() < height * 9 / 16) {
					// Clicked 2 Players
					
					bottomIsWinner = false;
					topIsWinner = false;
					speedX = random.nextInt(4) + 3;
					speedY = random.nextInt(4) + 3;
					gameMode = 4;
					menuMusic.pause();
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 10 / 16
						&& event.getY() < height * 12 / 16) {
					
					gameMode = 5;
					menuMusic.pause();
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 13 / 16
						&& event.getY() < height * 15 / 16) {
					
					gameMode = 6;
					menuMusic.pause();
				}
			}

		} else if (gameMode == 4) {
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 1 / 13
						&& event.getY() < height * 3 / 13) {
					if (wantMusic) {
						jeahao.setLooping(true);
						jeahao.start();
						jeahao.seekTo(0);
					}

					gameMode = 2;
					gameMode2 = 21;

				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 4 / 13
						&& event.getY() < height * 6 / 13) {
					if (wantMusic) {
						jeahao.setLooping(true);
						jeahao.start();
						jeahao.seekTo(0);
					}
					gameMode = 2;
					gameMode2 = 22;
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 7 / 13
						&& event.getY() < height * 9 / 13) {
					if (wantMusic) {
						jeahao.setLooping(true);
						jeahao.start();
						jeahao.seekTo(0);
					}
					gameMode = 2;
					gameMode2 = 23;
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 10 / 13
						&& event.getY() < height * 12 / 13) {
					gameMode = -1;
				}

			}
		} else if (gameMode == 5) {
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

				if (event.getX() > 0 && event.getX() < width) {
					gameMode = -1;
				}

			}

		} else if (gameMode == 6) {
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 4 / 16
						&& event.getY() < height * 6 / 16) {
					wantMusic = !wantMusic;
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 7 / 16
						&& event.getY() < height * 9 / 16) {
					wantSounds = !wantSounds;
				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 10 / 16
						&& event.getY() < height * 12 / 16) {
					currenthighscore = 0;
					edit.putInt("highscore", currenthighscore);
					edit.commit();

				}
				if (event.getX() > width * 1 / 16
						&& event.getX() < width * 15 / 16
						&& event.getY() > height * 13 / 16
						&& event.getY() < height * 15 / 16) {
					gameMode = -1;
				}
			}
		}

		return super.surfaceTouchEvent(event);

	}

	private void detectTouchEvent(MotionEvent event) {
		pointerCount = event.getPointerCount();
		// if (pointerCount>3){
		// pointerCount = 3;
		// }
		int pointerIndex = event.getActionIndex();

		if (speedY > 0) {

			if (event.getY(pointerIndex) > height / 2) {
				pong2Center = event.getX(pointerIndex);
				pong2Extra = pong2Center;
			}
			if (event.getY(pointerIndex) < height / 2) {

				pong1Extra = event.getX(pointerIndex);
				pong1Center = pong1Extra;
			}
		}
		if (speedY < 0) {
			if (event.getY(pointerIndex) > height / 2) {

				pong2Extra = event.getX(pointerIndex);
				pong2Center = pong2Extra;
			}
			if (event.getY(pointerIndex) < height / 2) {

				pong1Center = event.getX(pointerIndex);
				pong1Extra = pong1Center;
			}
		}

	}

	public void mousePressed() {

		if (gameMode == 1) {
			if (mouseX > width / 16 && mouseX < width * 15 / 16
					&& mouseY > height * 10 / 16 && mouseY < height * 12 / 16) {
				score0 = 0;
				speedX = random.nextInt(4) + 3;
				speedY = random.nextInt(4) + 3;
				posX = width / 2;
				posY = height / 2;
				pongRadius = width / 8;
				ellipseR = 255;
				ellipseG = 255;
				ellipseB = 255;
				if (wantMusic) {
					jeahao.setLooping(true);
					jeahao.start();
					deathMusic.pause();
				}
				if (wantSounds) {
					
				}
				gameMode = 0;
			}
			if (mouseX > width * 1 / 16 && mouseX < width * 15 / 16
					&& mouseY > height * 13 / 16 && mouseY < height * 15 / 16) {
				score0 = 0;
				speedX = random.nextInt(4) + 3;
				speedY = random.nextInt(4) + 3;
				posX = width / 2;
				posY = height / 2;
				pongRadius = width / 8;
				ellipseR = 255;
				ellipseG = 255;
				ellipseB = 255;
				if (wantMusic) {
					deathMusic.pause();
				}
				gameMode = -1;
			}

		}

		if (gameMode == 3) {
			if (mouseX > width * 1 / 16 && mouseX < width * 15 / 16
					&& mouseY > height * 22 / 64 && mouseY < height * 31 / 64) {

				if (bottomIsWinner || topIsWinner) {
					score = 0;
					score2 = 0;
				}
				speedX = random.nextInt(4) + 3;
				speedY = random.nextInt(4) + 3;
				posX = width / 2;
				posY = height / 2;
				pongRadius = width / 8;
				ellipseR = 255;
				ellipseG = 255;
				ellipseB = 255;
				if (wantMusic) {
					jeahao.setLooping(true);
					jeahao.start();
					deathMusic.pause();
				}
				if (wantSounds) {
					
				}
				bottomIsWinner = false;
				topIsWinner = false;
				gameMode = 2;
			}
			if (mouseX > width * 1 / 16 && mouseX < width * 15 / 16
					&& mouseY > height * 33 / 64 && mouseY < height * 42 / 64) {
				score = 0;
				score2 = 0;
				speedX = random.nextInt(4) + 3;
				speedY = random.nextInt(4) + 3;
				posX = width / 2;
				posY = height / 2;
				pongRadius = width / 8;
				ellipseR = 255;
				ellipseG = 255;
				ellipseB = 255;
				if (wantSounds) {
					
				}
				if (wantMusic){
					deathMusic.pause();
				}
					
				gameMode = -1;
			}
		}

	}

	@Override
	public void onPause() {
		deathMusic.pause();
		jeahao.pause();
		menuMusic.pause();
		super.onPause();
	}

	@Override
	public void onStop() {
		deathMusic.pause();
		jeahao.pause();
		menuMusic.pause();
		super.onStop();
	}

	@Override
	public void onResume() {
		if (gameMode == 0 || gameMode == 2) {
			if (wantMusic) {
				jeahao.start();
				
			}
		if (gameMode == -1){
			menuMusic.start();
			}
		

		}
		super.onResume();
	}

	public void onRestart() {
		if (gameMode == 0 || gameMode == 2) {
			if (wantMusic) {
				jeahao.start();
				
			}
			
			if (gameMode == -1){
				menuMusic.start();
				}
		}

		super.onRestart();

	}
}
