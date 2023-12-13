package allocateMem;

/**
 * @author JunBo Huang
 * @version 1.0
 */
public class MemoryBlock {
    int startAddress;
    int size;
    boolean allocated;
    int lastUsed; // For LRU algorithm

    public MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.allocated = false;
        this.lastUsed = 0;
    }
}

