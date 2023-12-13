package allocateMem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryManager {
    private List<MemoryBlock> memory;
    private LinkedList<MemoryBlock> freeBlocks;

    public MemoryManager() {
        memory = new ArrayList<>();
        freeBlocks = new LinkedList<>();
        initializeMemory();
    }

    private void initializeMemory() {
        // Set up memory blocks with start addresses and sizes
        memory.add(new MemoryBlock(0, 100));
        memory.add(new MemoryBlock(100, 150));
        memory.add(new MemoryBlock(250, 200));
        memory.add(new MemoryBlock(450, 100));

        // Initialize free blocks list
        freeBlocks.addAll(memory);
    }

    public void allocateMemory(int sizeToAllocate, String allocationAlgorithm) {
        boolean allocated = false;

        switch (allocationAlgorithm) {
            case "First Fit":
                allocated = firstFit(sizeToAllocate);
                break;
            case "Next Fit":
                allocated = nextFit(sizeToAllocate);
                break;
            case "LRU":
                allocated = lru(sizeToAllocate);
                break;
            case "MRU":
                allocated = mru(sizeToAllocate);
                break;
            case "LFU":
                allocated = lfu(sizeToAllocate);
                break;
            case "Clock":
                allocated = clock(sizeToAllocate);
                break;
            case "worstFit":
                allocated = worstFit(sizeToAllocate);
                break;
            case "bestFit":
                allocated = bestFit(sizeToAllocate);
                break;
            case "fifo":
                allocated = fifo(sizeToAllocate);
                break;
        }

        if (!allocated) {
            System.out.println("Memory allocation failed. Insufficient space available.");
        }
    }
    private boolean firstFit(int sizeToAllocate) {
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                block.allocated = true;
                freeBlocks.remove(block);
                return true;
            }
        }
        return false;
    }

    private boolean nextFit(int sizeToAllocate) {
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                block.allocated = true;
                freeBlocks.remove(block);
                return true;
            }
        }
        return false;
    }

    private boolean lru(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                if (selectedBlock == null || block.lastUsed < selectedBlock.lastUsed) {
                    selectedBlock = block;
                }
            }
        }
        if (selectedBlock != null) {
            selectedBlock.allocated = true;
            freeBlocks.remove(selectedBlock);
            return true;
        }
        return false;
    }
    private boolean mru(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                if (selectedBlock == null || block.lastUsed > selectedBlock.lastUsed) {
                    selectedBlock = block;
                }
            }
        }
        if (selectedBlock != null) {
            selectedBlock.allocated = true;
            freeBlocks.remove(selectedBlock);
            return true;
        }
        return false;
    }
    private boolean example(int sizeToAllocate) {
//        MemoryManager memoryManager = new MemoryManager(1000);
        allocate(200);
        printMemoryStatus();

        // Allocate 300 units of memory
        allocate(300);
        printMemoryStatus();

        // Allocate 150 units of memory
        allocate(150);
        printMemoryStatus();

        // Deallocate 20 units of memory
        deallocate(20);
        printMemoryStatus();

        return true;
    }

    private boolean lfu(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                if (selectedBlock == null || block.lastUsed < selectedBlock.lastUsed) {
                    selectedBlock = block;
                }
            }
        }
        if (selectedBlock != null) {
            selectedBlock.allocated = true;
            freeBlocks.remove(selectedBlock);
            return true;
        }
        return false;
    }

    private boolean clock(int sizeToAllocate) {
        int currentIndex = 0;
        int initialIndex = currentIndex;

        do {
            MemoryBlock currentBlock = freeBlocks.get(currentIndex);
            if (!currentBlock.allocated && currentBlock.size >= sizeToAllocate) {
                currentBlock.allocated = true;
                freeBlocks.remove(currentIndex);
                return true;
            }

            currentIndex = (currentIndex + 1) % freeBlocks.size();

        } while (currentIndex != initialIndex);

        return false;
    }

    public void printMemoryStatus() {
        System.out.println("Memory Status:");
        for (MemoryBlock block : memory) {
            System.out.println("Address: " + block.startAddress + ", Size: " + block.size + ", Allocated: " + block.allocated);
        }
        System.out.println();
    }
    public void allocate(int processSize) {
        for (MemoryBlock block : memory) {
            if (!block.allocated && block.size >= processSize) {
                // Found a sufficiently large free block for allocation
                block.allocated = true;

                // Simulate memory fragmentation: create a new free block if there's remaining space
                if (block.size > processSize) {
                    MemoryBlock remainingBlock = new MemoryBlock(block.startAddress + processSize, block.size - processSize);
                    memory.add(remainingBlock);
                }

                System.out.println("Allocated " + processSize + " units of memory at address " + block.startAddress);
                return;
            }
        }

        // Unable to find a suitable free block for allocation
        System.out.println("Unable to allocate memory for the process.");
    }
    public void deallocate(int address) {
        for (MemoryBlock block : memory) {
            if (block.allocated && block.startAddress == address) {
                // Deallocate the memory block at the specified address
                block.allocated = false;
                System.out.println("Deallocated memory at address " + address);
                return;
            }
        }

        // Unable to find a memory block at the specified address for deallocation
        System.out.println("Unable to deallocate memory. No block found at address " + address);
    }
    private boolean worstFit(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                if (selectedBlock == null || block.size > selectedBlock.size) {
                    selectedBlock = block;
                }
            }
        }
        return allocateBlock(selectedBlock);
    }

    // Best Fit Algorithm
    private boolean bestFit(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                if (selectedBlock == null || block.size < selectedBlock.size) {
                    selectedBlock = block;
                }
            }
        }
        return allocateBlock(selectedBlock);
    }

    // FIFO (First Fit) Algorithm
    private boolean fifo(int sizeToAllocate) {
        MemoryBlock selectedBlock = null;
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= sizeToAllocate) {
                selectedBlock = block;
                break;
            }
        }
        return allocateBlock(selectedBlock);
    }

    private boolean allocateBlock(MemoryBlock selectedBlock) {
        if (selectedBlock != null) {
            selectedBlock.allocated = true;
            freeBlocks.remove(selectedBlock);
            return true;
        }
        return false;
    }
}
