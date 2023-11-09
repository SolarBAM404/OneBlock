import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.lunardev.oneblock.OneBlockPlugin;
import com.lunardev.oneblock.block.BlockDetails;
import com.lunardev.oneblock.block.Level;
import com.lunardev.oneblock.block.LootPool;
import com.lunardev.oneblock.block.MobPool;
import dev.watchwolf.entities.Position;
import dev.watchwolf.entities.blocks.Block;
import dev.watchwolf.entities.blocks.Blocks;
import dev.watchwolf.entities.entities.Player;
import dev.watchwolf.entities.items.Item;
import dev.watchwolf.tester.AbstractTest;
import dev.watchwolf.tester.ExtendedClientPetition;
import dev.watchwolf.tester.TesterConnector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(BlockEventsTest.class)
class BlockEventsTest extends AbstractTest {

    private OneBlockPlugin plugin;
    private BlockDetails blockDetails;

    @Override
    public String getConfigFile() {
        return "src/test/resources/watchwolf.yml";
    }

    @ParameterizedTest
    @ArgumentsSource(BlockEventsTest.class)
    void restoreTheLastBrokenBlock(TesterConnector connector) throws IOException {
        /* decide where/who will interact with the event */
        String client = connector.getClients()[0];
        ExtendedClientPetition clientConnector = connector.getClientPetition(client);
        Position whereToPlaceTheBlock = clientConnector.getPosition().add(1,0,0); // block next to the user
        Block whatBlockWillBePlaced = Blocks.DIRT;

        /* prepare the environment (make sure the player can place the block there) */
        connector.server.setBlock(whereToPlaceTheBlock, Blocks.AIR);

        /* prepare the client (give him the block to place) */
        Item placedBlockItem = new Item(whatBlockWillBePlaced.getItemType());
        connector.server.giveItem(client, placedBlockItem);

        /* start the test */
        clientConnector.setBlock(whatBlockWillBePlaced, whereToPlaceTheBlock); // place the block
        clientConnector.runCommand("ctrl-z"); // undo

        assertEquals(Blocks.AIR, connector.server.getBlock(whereToPlaceTheBlock)); // we expect air in the place we've put the block
//        assertEquals(1, getItemAmounts(clientConnector.getInventory().getItems()).get(whatBlockWillBePlaced.getItemType())); // we expect 1 dirt block back
    }


    public Level setupBasicLevel() {
        Material[] blockPool = new Material[] {
                Material.GRASS_BLOCK,
                Material.STONE,
                Material.AMETHYST_BLOCK,
                Material.ACACIA_LOG,
                Material.BAMBOO_BLOCK,
        };

        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Material.DIAMOND_BLOCK),
                new ItemStack(Material.NETHER_BRICK)
        };
        List<ItemStack[]> itemStacks = new ArrayList<>();
        itemStacks.add(stacks);
        LootPool lootPool = new LootPool(itemStacks);

        EntityType[] types = {
                EntityType.ZOMBIE
        };
        List<EntityType[]> mobs = new ArrayList<>();
        mobs.add(types);
        MobPool mobPool = new MobPool(mobs);

        return new Level(0, blockPool, lootPool, mobPool);
    }

    BlockDetails getBlockDetails(Player player, Position position) {
        Level level = setupBasicLevel();
        World world = Bukkit.getWorld(position.getWorld());
        return new BlockDetails(level,
                new Location(world, position.getBlockX(), position.getBlockY(), position.getBlockZ()), Bukkit.getPlayer(player.getUUID()));
    }

    @lombok.SneakyThrows
    @ParameterizedTest
    @ArgumentsSource(BlockEventsTest.class)
    void BlockBrokenRegenerateTest(TesterConnector connector) {
        String client = connector.getClients()[0];
        ExtendedClientPetition clientConnector = connector.getClientPetition(client);
        try {
            Position position = clientConnector.getPosition();
            Block block = connector.getBlock(position);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(blockDetails.getCurrentBlock(), blockDetails.getLocation().getBlock().getType());

        assertEquals(blockDetails.getCurrentBlock(), blockDetails.getLocation().getBlock().getType());
    }

    @Test
    void BlockSpawningTest() {
        blockDetails.spawnBlock();
        assertNotNull(blockDetails.getCurrentBlock());
        assertEquals(blockDetails.getCurrentBlock(), blockDetails.getLocation().getBlock().getType());
    }

}