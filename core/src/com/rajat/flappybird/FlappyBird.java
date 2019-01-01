package com.rajat.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background,bird[],bottomtube1,toptube1,bottomtube2,toptube2;
    int TotalWidth;
    BitmapFont text,start,board;
    int TotalHeight;
    float curY;
    float velocity=1;
    int gap;
    int gamestate=1;
    int birdState=1;
    double pipe1xb,pipe1xt;
    double pipe2xb,pipe2xt;
    double pipe1yb,pipe1yt;
    double pipe2yb,pipe2yt;
    Random r;
    int deviation1,deviation2;
    int hardness=500;
    int score=0;
    int pipeSelected=0;

	@Override
	public void create () {
		batch = new SpriteBatch();
	    background=new Texture("bg.png");
	    bird=new Texture[3];
	    bird[0]=new Texture("bird.png");
        bird[1]=new Texture("bird2.png");
        bird[2]=new Texture("bird3.png");
        bottomtube1=new Texture("bottomtube.png");
        toptube1=new Texture("toptube.png");
        bottomtube2=new Texture("bottomtube.png");
        toptube2=new Texture("toptube.png");
	    TotalHeight=Gdx.graphics.getHeight();
	    TotalWidth=Gdx.graphics.getWidth();
	    curY=TotalHeight/2-bird[birdState].getHeight()/2;
	    text=new BitmapFont();
	    text.setColor(Color.RED);
        text.getData().setScale(4,4);
        start=new BitmapFont();
        board=new BitmapFont();
        board.getData().setScale(4,4);
        board.setColor(Color.GREEN);
        start.getData().setScale(4,4);
        gap=TotalHeight/5;
        pipe1xb=TotalWidth;
        pipe1xt=TotalWidth;
        pipe2xb=TotalWidth+TotalWidth*.5;
        pipe2xt=TotalWidth+TotalWidth*.5;
        r=new Random();
        pipeSelected=0;
	    deviation1=r.nextInt(hardness)-hardness/2;
        deviation2=r.nextInt(hardness)-hardness/2;
	}
    void collission(){
	    //FOR pipe 1
	    int temp11=TotalWidth/2-(bird[birdState].getWidth()/2)+bird[birdState].getWidth();
        int temp21=TotalWidth/2-(bird[birdState].getWidth()/2)-bird[birdState].getWidth()-50;
        if(pipeSelected==0){
            if(pipe1xt<temp11&&pipe1xt>temp21&&(curY+bird[birdState].getHeight()>pipe1yt||curY<pipe1yb+bottomtube1.getHeight())){
                birdState=2;
                gamestate=3;
            }
        }
        else if(pipeSelected==1){
            if(pipe2xt<temp11&&pipe2xt>temp21&&(curY+bird[birdState].getHeight()>pipe2yt||curY<pipe2yb+bottomtube2.getHeight())){
                birdState=2;
                gamestate=3;
            }
        }

        if(pipe1xt==temp21){
	        score++;
	        pipeSelected=1;
        }
        if(pipe2xt==temp21){
            score++;
            pipeSelected=0;
        }
    }
	@Override
	public void render () {
        batch.begin();

        if(gamestate==1){
            birdState=0;
            velocity=0;
            score=0;
            pipeSelected=0;
            curY=TotalHeight/2-bird[birdState].getHeight()/2;
            batch.draw(background,0,0,TotalWidth,TotalHeight);
            batch.draw(bird[birdState],TotalWidth/2-(bird[birdState].getWidth()/2),curY);
            if(Gdx.input.justTouched()){
                gamestate=2;
                pipe1xb=TotalWidth;
                pipe1xt=TotalWidth;
                pipe2xb=TotalWidth+TotalHeight*.5;
                pipe2xt=TotalWidth+TotalHeight*.5;
            }

        }
	    else if(gamestate==2){

            if(birdState==0){
                birdState=1;
            }
            else{
                birdState=0;
            }
            if(pipe1xt<(-bottomtube1.getWidth())||pipe1xb<(-bottomtube1.getWidth())){
                pipe1xt=TotalWidth+TotalWidth*.5;
                pipe1xb=TotalWidth+TotalWidth*.5;
                deviation1=r.nextInt(hardness)-hardness/2;
                Gdx.app.log("Deviation1",""+deviation1);
            }
            if(pipe2xt<(-bottomtube2.getWidth())||pipe2xb<(-bottomtube2.getWidth())){
                pipe2xt=TotalWidth+TotalWidth*.5;
                pipe2xb=TotalWidth+TotalWidth*.5;
                deviation2=r.nextInt(hardness)-hardness/2;
                Gdx.app.log("Deviation2",""+deviation2);
            }


            if(curY>0&&curY<TotalHeight-bird[birdState].getHeight()){
                curY=curY-velocity;
                velocity=velocity+.8f;
            }
            else{
                birdState=2;
                gamestate=3;
            }
            if(Gdx.input.justTouched()){
                velocity=velocity-20;
            }

            pipe1xb-=2;
            pipe1xt-=2;
            pipe2xb-=2;
            pipe2xt-=2;
            pipe1yb=TotalHeight/2-bottomtube1.getHeight()-gap/2+deviation1;
            pipe1yt=TotalHeight/2+gap/2+deviation1;
            pipe2yb=TotalHeight/2-bottomtube1.getHeight()-gap/2+deviation2;
            pipe2yt=TotalHeight/2+gap/2+deviation2;
            batch.draw(background,0,0,TotalWidth,TotalHeight);
            batch.draw(bird[birdState],TotalWidth/2-(bird[birdState].getWidth()/2),curY);
            batch.draw(bottomtube1,(int)pipe1xb,(int)pipe1yb);
            batch.draw(toptube1,(int)pipe1xt,(int)pipe1yt);
            batch.draw(bottomtube2,(int)pipe2xb,(int)pipe2yb);
            batch.draw(toptube2,(int)pipe2xt,(int)pipe2yt);
            board.draw(batch,"SCORE:"+score,10,TotalHeight-10);
            collission();
        }
        else{
            if(curY>-bird[2].getHeight()){
                curY=curY-10;
            }
            batch.draw(background,0,0,TotalWidth,TotalHeight);
            batch.draw(bottomtube1,(int)pipe1xb,(int)pipe1yb);
            batch.draw(toptube1,(int)pipe1xt,(int)pipe1yt);
            batch.draw(bottomtube2,(int)pipe2xb,(int)pipe2yb);
            batch.draw(toptube2,(int)pipe2xt,(int)pipe2yt);
            batch.draw(bird[birdState],TotalWidth/2-(bird[birdState].getWidth()/2),curY);
            text.draw(batch,"Game Over",TotalWidth/2-150,TotalHeight/2+100);
            start.draw(batch,"Tap Again To play",TotalWidth/2-200,TotalHeight/2-100);
            board.draw(batch,"Your SCORE: "+score,TotalWidth/2-200,TotalHeight/2);
            if(Gdx.input.justTouched()){
                gamestate=1;
            }
        }
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
