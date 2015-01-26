static int year = 1859;
Particle[] particles;
JSONArray array;
PImage pg;
Next_Year test = new Next_Year();
float x = 9;
float velocity = x;
float antiVelocity = -x;
float acceleration = -.08;
ArrayList<Particle> wave = new ArrayList<Particle>();
boolean clicked = false;
 
 
void setup()
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
 
void draw()
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
 
 
void getData()
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

void update()
{
  year++;
  background(pg);
  writeYear(); 
  wave = test.nextYear(particles, Integer.toString(year));
}

void writeYear()
{
 fill(0);
 textSize(33);
 text("Year: " + year, 15, 45); 
}

void mouseClicked()
{
  clicked = true; 
}

void keyPressed()
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





