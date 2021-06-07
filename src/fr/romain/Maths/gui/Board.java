package fr.romain.Maths.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import fr.romain.Maths.geom2D.Point;

public class Board extends JPanel{
	
	private static final long serialVersionUID = -4463720981116411725L;
	
	Graphics2D pen;
	
	private MouseAdapter mouse;
	
	/**
	 * The min and max coordinates of the visible part of the board
	 */
	private Point min,max;
	
	public Board(double xMin, double xMax, double yMin, double yMax) {
		setBackground(Color.red);
		min = new Point(xMin, yMin);
		max = new Point(xMax, yMax);
		
	}
	
	public Board() {
		this(-1, 10, -1, 10);
	}
	
	public int getXBoard(double xFrame) {
		return (int) ((xFrame - min.getX()) * getWidth() / (max.getX() - min.getX()));
	}
	
	public int getYBoard(double yFrame) {
		return getHeight() - (int) ((yFrame - min.getY()) * getHeight() / (max.getY() - max.getY()));
	}
	
	public double getXFrame(int xBoard) {
		return min.getX() + ((double)xBoard) / getHeight() * (max.getX() - min.getX());
	}
	
	public double getYFrame(int yBoard) {
		return (getHeight() - yBoard) * (max.getY() - min.getY()) / ((double) getHeight()) + min.getY();
	}
	
	public Point getCursorPoint(MouseEvent e) {
		return new Point(getXFrame(e.getX()), getYFrame(e.getY()));
	}

	public Point getMin() {
		return min;
	}

	public void setMin(Point min) {
		this.min = min;
	}

	public Point getMax() {
		return max;
	}

	public void setMax(Point max) {
		this.max = max;
	}
	
	
	public MouseAdapter getMouse() {
		return mouse;
	}

	public void setMouse(MouseAdapter mouse) {
		this.mouse = mouse;
	}

	public void drawCircle(Point center, double radius) {
        int xBoard = getXBoard(center.getX());
        int yBoard = getYBoard(center.getY());
        int rrx = (int) (radius * this.getWidth() / (max.getX() - min.getX()));
        int rry = (int) (radius * this.getHeight() / (max.getY() - min.getX()));
        
        pen.drawOval(xBoard - rrx, yBoard - rry, 2 * rrx, 2 * rry);
	}
	
	public void drawPoint(Point point) {
		pen.fillOval(getXBoard(point.getX()) - 5, getYBoard(point.getY()) - 5, 10, 10);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		pen = (Graphics2D) g;
	}

}





