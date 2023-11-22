//package com.lunardev.oneblock.block;
//
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.OfflinePlayer;
//import org.bukkit.World;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BlockDetailsTest {
//
//    @Mock
//    private Level mockLevel;
//
//    @Mock
//    private OfflinePlayer mockPlayer;
//
//    @Mock
//    private World mockWorld;
//
//    private BlockDetails blockDetails;
//    private Location location;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        location = new Location(mockWorld, 0, 0, 0);
//        blockDetails = new BlockDetails(mockLevel, location, mockPlayer);
//    }
//
//    @Test
//    public void testConstructor() {
//        assertEquals(mockLevel, blockDetails.getCurrentLevel());
//        assertEquals(location, blockDetails.getLocation());
//        assertEquals(mockPlayer, blockDetails.getOwner());
//    }
//
//    @Test
//    public void testSpawnBlockMaterial() {
//        Material testMaterial = Material.STONE;
//        blockDetails.spawnBlock(testMaterial);
//        assertEquals(testMaterial, blockDetails.getCurrentBlock());
//    }
//
//    @Test
//    void testIsLocationTaken() {
//        Location testLocation = new Location(mockWorld, -2000, 0, -2000);
//        Location testLocation2 = new Location(mockWorld, -2500, 0, -2500);
//        Location testLocation3 = new Location(mockWorld, -4000, 0, -2000);
//        BlockDetails.setLocationTaken(testLocation);
//        assertTrue(BlockDetails.isLocationTaken(testLocation));
//        assertTrue(BlockDetails.isLocationTaken(testLocation2));
//        assertFalse(BlockDetails.isLocationTaken(testLocation3));
//    }
//}