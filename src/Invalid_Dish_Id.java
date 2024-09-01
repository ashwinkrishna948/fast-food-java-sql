class Invalid_Dish_Id extends Exception{
   String a;
   Invalid_Dish_Id(String b) {
     a=b;
   }
   public String toString(){
     return (a) ;
  }
}