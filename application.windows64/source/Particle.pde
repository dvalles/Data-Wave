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
 
  void display()
  {
   s = 300/wave.size() + 35;
   t = (pos_y/255)*255;
   
   fill(0, 60, 255, 255/(t/100) + 100);
   noStroke();
   ellipse(pos_x, pos_y, s, s);
   pos_y += velocity;
  }
  
  void isOver()
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

