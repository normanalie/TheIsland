package org.example.GUI.gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface StateInterface {
public void update();
public void draw(Graphics g);
public void mouseClicked(MouseEvent e);
public void mousePressed(MouseEvent e);
public void mouseReleased(MouseEvent e);





}
