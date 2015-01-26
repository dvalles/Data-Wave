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
