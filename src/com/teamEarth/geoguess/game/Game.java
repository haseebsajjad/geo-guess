package com.teamEarth.geoguess.game;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

public class Game {
	private DifficultyLevel diffLevel;
	private int noOfQuestions, currentQuestion;
	private int []testQuestions;
	private SQLiteDatabase db;
	private Cursor cursor;
	private Context context;
	private java.util.ArrayList<Question> questions;


	public Game(Context context){
		this.context=context;
	}
	
	public void init(){
		diffLevel=DifficultyLevel.EASY;
		this.noOfQuestions=10;
		testQuestions=new int[noOfQuestions];
		currentQuestion=0;
		if( questions==null)
			questions=new java.util.ArrayList<Question>();
		else{
			for(Question q: questions){
				q.clearQuestion();
			}
		}
	}
	
	public void setDifficulty(DifficultyLevel diffLevel){
		this.diffLevel=diffLevel;
	}
	
	public void loadQuestions(){
		Question q=null;
        db=(new DB(context)).getWritableDatabase();
        try{
			cursor=db.query(false, "images", new String[] {"id _id","image","url","ans"}, null, null, null, null, null, null);
			if( cursor.moveToFirst()){
				do{
					q=new Question();
					q.fillQuestion(cursor.getString(1), cursor.getString(2), cursor.getString(3));
					questions.add(q);
				}while(cursor.moveToNext());
			}
            cursor.close();
            addChoices();
            selectTestQuestions();
        }
        catch(Exception ex){
        	Log.v("Load Question", ex.getMessage());
        }
		
	}
	
	public void addChoices(){
		String []ch=new String[4];
		int i,j,correctAns,tmp;
		boolean notFound;
		
        for(Question q: questions){
        	correctAns=(int)(Math.random()*4);
        	ch[correctAns]=q.ans;
        	for(i=0;i<4;i++){
        		if( i==correctAns)
        			continue;
        		else{
        			do{
        				tmp=(int)(Math.random()*questions.size());
        				notFound=true;
        				for(j=0;j<i;j++)
        					if( questions.get(tmp).ans.compareTo(ch[j]) ==0 || q.ans.compareTo(ch[j])==0)
        						notFound=false;
        			}while(notFound);
        		}
        	}
        	q.fillChoices(ch[0],ch[1], ch[2], ch[3]);
        }
        ch=null;
	}
	
	public void selectTestQuestions(){
		int i,j;
		boolean notFound;
		for(i=0;i<noOfQuestions;i++){
			do{
				notFound=true;
				testQuestions[i]=(int)(Math.random()*questions.size());
				for(j=0;j<i;j++){
					if(testQuestions[i]==testQuestions[j])
						notFound=false;
				}
			}while(notFound);
		}
	}
	
	public boolean getNextQuestion(Question question){
		if( currentQuestion<noOfQuestions){
			question=questions.get(testQuestions[currentQuestion]);
			return true;
		}
		else
			return false;
	}
}
