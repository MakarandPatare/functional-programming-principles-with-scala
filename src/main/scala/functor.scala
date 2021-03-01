/**
 * Functor 
 * -- is a FP principle which is used to do mapping between two types/catagories
 * -- is used to create a structure which holds things inside and provides facility
 * to unwrap and wrap things
 * -- has to provide two functionalities, one to do mapping and other to wrap things
 * Rules
 * - Identity Rule - unit()
 * - Associativity Rule - associative behavior of map() 
 * Implementation
 * - Has a map() higher order function and 
 * - Has an unit() function inside along with things
 */

object Functor {
    /** Functor definition */
    case class Container[A](things: A) {

        /** It upwraps the bag, gets things out of it, processes it, wraps it again inside the bag, returns bag with things */
        def map[B](fn: A => B): Container[B] = {
            Container(fn(things))
        }
   
        /**
         * Identity Rule
         * 1) Take input and return the same as output
         * 2) Proves that map exactly and only does what it says
         * 3) Proves that map is a pure function
        */
        /** def identity[A](things:A) :A = things [Not needed now as apply in companion object serves as unit()] */
    }

    /** Functor examples */
    abstract class Eatable
    case class Almonds(breed: String, quantity: Double) extends Eatable
    case class Rice(breed: String, quantity: Double) extends Eatable
    case class Peanuts(breed: String, quantity: Double) extends Eatable

    val almondBag = Container[Almonds](Almonds("Indian Almonds", 10))
    val doubleAlmonds = (things: Almonds) => Almonds(things.breed, things.quantity * 2)
    val saltAlmonds = (things: Almonds) => Almonds(things.breed + " Salted", things.quantity)
    val deepFryAlmonds = (things: Almonds) => Almonds(things.breed + " Deep Fried", things.quantity)

    val doubleAlmondsBag = almondBag.map(doubleAlmonds)
    val saltedAlmondsBag = almondBag.map(saltAlmonds)
    val deepFriedAlmondsBag = almondBag.map(deepFryAlmonds)

    println(almondBag)
    println(doubleAlmondsBag)
    println(saltedAlmondsBag)
    println(deepFriedAlmondsBag)

    /** Apply and check the identity rule on map */
    val almondsBagIdentity = almondBag.map(identity)
    if (almondBag == almondsBagIdentity)
        println("Identity rule passed for the Map")
    else
        println("Identity rule failed for the Map")
        
    /** Associativity rule
     * Map can be used with chaining
     * Used to validate that devs are not forced to combine multiple functions together into a single function before applying it to arguments
     * Used to validate that map outcome is always same if called in chain or called only once with composed function 
     * */

    val andThenExampleFn = doubleAlmonds andThen saltAlmonds andThen deepFryAlmonds // Left to right
    val composeExampleFn = deepFryAlmonds compose saltAlmonds compose doubleAlmonds // right to left

    val bag1 = almondBag.map(doubleAlmonds).map(saltAlmonds).map(deepFryAlmonds)
    val bag2 = almondBag.map(things => deepFryAlmonds(saltAlmonds(doubleAlmonds(things))))
    val bag3 = almondBag.map(things => andThenExampleFn(things))
    val bag4 = almondBag.map(things => composeExampleFn(things))

    println(bag1)
    println(bag2)
    println(bag3)
    println(bag4)
    
    if (bag1 == bag2 && bag1 == bag3 && bag1 == bag4)
        println("Associativity passed")
    else
        println("associativity failed")
}
