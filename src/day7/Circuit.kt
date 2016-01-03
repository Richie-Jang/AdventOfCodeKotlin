package day7

import java.util.*

/**
 * Created by richi on 2015-12-31.
 */
enum class Gates {
    AND,OR,LSHIFT,RSHIFT,NOT,ASSIGN
}

sealed class Circuit {
    class Value (val value: Int) : Circuit() {
        override fun equals(other: Any?): Boolean {
            if (other is Value) {
                return this.value == other.value
            } else {
                return false
            }
        }
        override fun hashCode(): Int {
            return value;
        }
    }
    class Wire (val name: String) : Circuit() {
        override fun equals(other: Any?): Boolean {
            if (other is Wire) {
                return other.name == this.name
            } else {
                return false
            }
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }
    class BinaryOp (val op: Gates, val c1: Circuit, val c2: Circuit, val cwire: Wire): Circuit() {
        // true : Eval Okay
        fun eval (map: HashMap<String, Int>): Boolean {
            // find value
            val v1 = when (c1) {
                is Wire -> {
                    if (map.containsKey(c1.name)) map.get(c1.name)!!
                    else return false
                }
                is Value -> c1.value
                else -> return false
            }
            val v2 = when (c2) {
                is Wire -> {
                    if (map.containsKey(c2.name)) map.get(c2.name)!!
                    else return false
                }
                is Value -> c2.value
                else -> return false
            }

            when (op) {
                Gates.AND -> map.put(cwire.name, v1.and(v2).and(0xFFFF))
                Gates.OR  -> map.put(cwire.name, v1.or(v2).and(0xFFFF))
                Gates.LSHIFT -> map.put(cwire.name, v1.shl(v2).and(0xFFFF))
                Gates.RSHIFT -> map.put(cwire.name, v1.shr(v2).and(0xFFFF))
            }
            return true
        }
    }
    class UnaryOp (val op: Gates, val c1: Circuit, val cwire: Wire): Circuit() {
        fun eval (map: HashMap<String, Int>): Boolean {
            val v1 = when (c1) {
                is Wire -> if (map.containsKey(c1.name)) map.get(c1.name)!!
                            else return false
                is Value -> c1.value
                else -> return false
            }
            when (op) {
                Gates.ASSIGN -> map.put(cwire.name, v1)
                Gates.NOT    -> map.put(cwire.name, v1.inv().and(0xFFFF))
            }
            return true
        }
    }
}

fun CreateValue (v: Int) : Circuit.Value = Circuit.Value(v)
fun CreateWire (v: String): Circuit.Wire = Circuit.Wire(v)
fun CreateWireOrValue(v: String): Circuit {
    try {
        val i = Integer.parseInt(v)
        return CreateValue(i.and(0xFFFF))
    } catch (e: Exception){
        return CreateWire(v)
    }
}