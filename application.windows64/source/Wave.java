import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Wave extends PApplet {

static int year = 1859;
Particle[] particles;
JSONArray array;
PImage pg;
Next_Year test = new Next_Year();
float x = 9;
float velocity = x;
float antiVelocity = -x;
float acceleration = -.08f;
ArrayList<Particle> wave = new ArrayList<Particle>();
boolean clicked = false;
 
 
public void setup()
{
 size(800,800);
 background(194, 178, 128);
 getData();
 
 for (int a = 0; a < 36001; a++)
 {
   float x = random(800);
   float y = random(800);
   point(x,y);
 }
 
 stroke(230);
 for (int a = 0; a < 35001; a++)
 {
   float x = random(800);
   float y = random(800);
   point(x,y);
 }
 
 saveFrame("background.jpg");
 pg = loadImage("background.jpg");
 update();
}
 
public void draw()
{
  if (wave.size() == 0) {update();}
  if (velocity < antiVelocity){update(); velocity = x; antiVelocity = -x;}
  else
 {
   background(pg);
   writeYear();
   for(int y = 0; y < wave.size(); y++)
   {
   wave.get(y).display();
   wave.get(y).isOver();  
   }
   velocity = velocity + acceleration;
   clicked = false;
 }
 }
 
 
public void getData()
{
  JSONObject data = loadJSONObject("http://api.dp.la/v2/items?sourceResource.collection=Georgia&api_key=87ac65635d4d45203a6817bbc97f8bbb&page_size=80000");
  JSONArray results = data.getJSONArray("docs");
  particles = new Particle[results.size()];
 
  for (int i = 0; i < results.size(); i++)
  {
    JSONObject temp = results.getJSONObject(i);
    Particle part = new Particle(temp);
    particles[i] = part;
  }
}

public void update()
{
  year++;
  background(pg);
  writeYear(); 
  wave = test.nextYear(particles, Integer.toString(year));
}

public void writeYear()
{
 fill(0);
 textSize(33);
 text("Year: " + year, 15, 45); 
}

public void mouseClicked()
{
  clicked = true; 
}

public void keyPressed()
{
   if(key == 's')
   {
     velocity = velocity / 2;
     acceleration = acceleration / 6;
   }
   if(key == 'f')
  {
    velocity = velocity*2;
    acceleration = acceleration * 6; 
  } 
}





public class Next_Year
{
  int x = 1;
  String tots = "";
  String tots2 = "";
  
  public Next_Year()
  {
   
  }
 
 public ArrayList<Particle> nextYear(Particle[] data, String next_year)
  {
   ArrayList<Particle> curr_year = new ArrayList<Particle>();
   for (int x = 0; x < data.length; x++)
   {
     try
     {
       tots = data[x].stored_data.getJSONObject("sourceResource").getJSONObject("date").getString("begin");
       tots2 = data[x].stored_data.getJSONObject("sourceResource").getJSONObject("date").getString("end");     
     }
     catch(Exception e){}
     if (next_year.equals(tots) || next_year.equals(tots2))
         curr_year.add(data[x]);
   }
   return curr_year;
  }
}
public class Particle
{
  float opacity;
  JSONObject stored_data;
  float pos_x, pos_y;
  float s;
  float t;
 
  public Particle()
  {
  }
 
  public Particle(JSONObject blu)
  {
    stored_data = blu;
    pos_x = random(0, 750);
    pos_y = random(-550, 0);
    
  }
 
  public void display()
  {
   s = 300/wave.size() + 35;
   t = (pos_y/255)*255;
   
   fill(0, 60, 255, 255/(t/100) + 100);
   noStroke();
   ellipse(pos_x, pos_y, s, s);
   pos_y += velocity;
  }
  
  public void isOver()
  {
   float dist = dist(mouseX, mouseY, pos_x, pos_y);
   if (dist < s)
    {
      fill(0);
      textSize(13);
      text(stored_data.getJSONObject("sourceResource").getString("title"), pos_x, pos_y);
    if (clicked)
       link(stored_data.getString("isShownAt"));   
    } 
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Wave" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
