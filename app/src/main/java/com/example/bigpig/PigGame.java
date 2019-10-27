package com.example.bigpig;

import java.util.Random;

public class PigGame {
	
	private Random rand = new Random();
	private int player1Score;
	private int player2Score;
	private int turnPoints;
	private String player1Name = "";
	private String player2Name = "";
	private int turn;
	
	public PigGame()
	{
		player1Score = 0;
		player2Score = 0;
		turnPoints = 0;
		turn = 1; // player 1 goes first
	}
	
	public PigGame(int p1Score, int p2Score, int tPoints, int t)
	{
		player1Score = p1Score;
		player2Score = p2Score;
		turnPoints = tPoints;
		turn = t;
	}
	
	public void setPlayer1Name(String n)
	{
		player1Name = n;
	}
	
	public String getPlayer1Name()
	{
		return player1Name;
	}
	
	public void setPlayer2Name(String n)
	{
		player2Name = n;
	}
	
	public String getPlayer2Name()
	{
		return player2Name;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getPlayer1Score()
	{
		return player1Score;
	}

	
	public int getPlayer2Score()
	{
		return player2Score;
	}
	
	public void resetGame()
	{
		player1Score = 0;
		player2Score = 0;
		turnPoints = 0;
		turn = 1;
	}

	public int rollDie(int evilDie, int numRolls)
	{
        int roll = rand.nextInt(8) + 1;

	    if (numRolls >1) {
            int roll2 = rand.nextInt(8) + 1;

            if (numRolls == 3) {
                int roll3 = rand.nextInt(8) + 1;
                if (roll != evilDie && roll2 != evilDie && roll3 != evilDie) {
                    turnPoints += roll + roll2 + roll3;
                } else {
                    turnPoints = 0;
                    changeTurn();
                }
                return roll3;
            }
            else {
                if (roll != evilDie && roll2 != evilDie) {
                    turnPoints += roll + roll2;
                    }
                else {
                    turnPoints = 0;
                    changeTurn();
                    return evilDie;
                }
                return roll2;
            }
	    }
	    else {
            if (roll != evilDie) {
                turnPoints += roll;
            } else {
                turnPoints = 0;
                changeTurn();
                return evilDie;
            }
        }
            return roll;
	}
	
	public int getTurnPoints()
	{
		return turnPoints;
	}

	public String getCurrentPlayer()
	{
		if(turn % 2 == 1)
			return player1Name;
		else
			return player2Name;
	}
	
	public int changeTurn()
	{
		if(turn % 2 == 1)
			player1Score += turnPoints;
		else
			player2Score += turnPoints;
		
		turnPoints = 0;
		
		turn++;
		return turn;
	}
	
	public String checkForWinner(int winning_score)
	{
       String winnerMessage = "";
        if (player1Score >= winning_score || player2Score >= winning_score) {
            if (player2Score > player1Score) {
                winnerMessage = String.format("%s wins!", player2Name);
            }
            // Player 1 can only win after player 2 has had thier turn
            // This is so both players can have an equal number of turns
            else if (player1Score > player2Score && turn % 2 == 1) {
                winnerMessage = String.format("%s wins!", player1Name);
            } else if (player1Score == player2Score) {
                winnerMessage = "Tie";
            }
        }
		return winnerMessage;
	}
	
}
