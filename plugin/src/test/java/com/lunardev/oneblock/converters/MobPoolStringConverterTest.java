//package com.lunardev.oneblock.converters;
//
//import org.bukkit.entity.EntityType;
//import com.lunardev.oneblock.block.entities.Mob;
//
//class MobPoolStringConverterTest {
//
//    private final MobPoolStringConverter mobPoolStringConverter = new MobPoolStringConverter();
//
//    @Test
//    void testDoForward() {
//        Mob[] mobs = new Mob[]{
//                new Mob("Lunar", 1, 100, EntityType.ZOMBIE),
//                new Mob("Solar", 1, 100, EntityType.SKELETON),
//                new Mob("Border", 1, 100, EntityType.ZOMBIE),
//        };
//
//        String[] expected = new String[]{
//                "ZOMBIE, Lunar, 1, 100",
//                "SKELETON, Solar, 1, 100",
//                "ZOMBIE, Border, 1, 100",
//        };
//
//        assertArrayEquals(expected, mobPoolStringConverter.doForward(mobs));
//    }
//
//    @Test
//    void testDoBackward() {
//        String[] strings = new String[]{
//                "ZOMBIE, Lunar, 1, 100",
//                "SKELETON, Solar, 1, 100",
//                "ZOMBIE, Border, 1, 100",
//        };
//
//        Mob[] expected = new Mob[]{
//                new Mob("Lunar", 1, 100, EntityType.ZOMBIE),
//                new Mob("Solar", 1, 100, EntityType.SKELETON),
//                new Mob("Border", 1, 100, EntityType.ZOMBIE),
//        };
//
//        Mob[] mobs = mobPoolStringConverter.doBackward(strings);
//        for (int i = 0; i < mobs.length; i++) {
//            assertEquals(expected[i].getName(), mobs[i].getName());
//            assertEquals(expected[i].getAmount(), mobs[i].getAmount());
//            assertEquals(expected[i].getChance(), mobs[i].getChance());
//            assertEquals(expected[i].getEntityType(), mobs[i].getEntityType());
//        }
//    }
//}