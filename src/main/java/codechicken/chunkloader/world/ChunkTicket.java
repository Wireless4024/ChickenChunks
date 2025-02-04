package codechicken.chunkloader.world;

import codechicken.chunkloader.ChickenChunks;
import codechicken.chunkloader.api.IChunkLoader;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeChunkManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds a reference to all IChunkLoaders currently loading a given chunk.
 * Automatically allocates and frees a vanilla Ticket.
 * <p>
 * Created by covers1624 on 11/5/20.
 */
public class ChunkTicket {

    private final ServerWorld level;
    private final ChunkPos pos;

    private final Set<IChunkLoader> loaders = new HashSet<>();

    public ChunkTicket(ServerWorld level, ChunkPos pos) {
        this.level = level;
        this.pos = pos;
    }

    /**
     * Adds an {@link IChunkLoader} to this ticket.
     *
     * @param loader The loader to add.
     * @return If this chunk had no loaders previously.
     */
    public boolean addLoader(IChunkLoader loader) {
        boolean empty = loaders.isEmpty();
        if (loaders.add(loader)) {
            ForgeChunkManager.forceChunk(level, ChickenChunks.MOD_ID, loader.pos(), pos.x, pos.z, true, true);
        }
        return empty;
    }

    /**
     * Removes an {@link IChunkLoader} from this ticket.
     *
     * @param loader The loadder to remove.
     * @return If the chunk is now empty.
     */
    public boolean remLoader(IChunkLoader loader) {
        if (loaders.remove(loader)) {
            ForgeChunkManager.forceChunk(level, ChickenChunks.MOD_ID, loader.pos(), pos.x, pos.z, false, true);
        }
        return loaders.isEmpty();
    }

    public Set<IChunkLoader> getLoaders() {
        return Collections.unmodifiableSet(loaders);
    }
}
