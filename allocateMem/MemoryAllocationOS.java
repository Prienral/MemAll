package allocateMem;

import java.util.Scanner;

public class MemoryAllocationOS {
    private static MemoryManager memoryManager;

    public static void main(String[] args) {
        initializeMemoryManager();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the size to allocate (Enter 0 to exit): ");
            int sizeToAllocate = scanner.nextInt();

            if (sizeToAllocate == 0) {
                break;
            }

            getUserInput(sizeToAllocate);

            memoryManager.printMemoryStatus();
        }
    }

    private static void initializeMemoryManager() {
        memoryManager = new MemoryManager();
    }

    private static void getUserInput(int sizeToAllocate) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose memory allocation algorithm (1: First Fit, 2: Next Fit, 3: LRU,, 4: Example,5: MRU, 6: LFU, 7: Clock, 8: worstFit, 9:bestFit, 10: fifo): ");
        int algorithmChoice = scanner.nextInt();

        String allocationAlgorithm;
        switch (algorithmChoice) {
            case 1:
                allocationAlgorithm = "First Fit";
                break;
            case 2:
                allocationAlgorithm = "Next Fit";
                break;
            case 3:
                allocationAlgorithm = "LRU";
                break;
            case 4:
                allocationAlgorithm = "Example";

            case 5:
                allocationAlgorithm = "MRU";
                break;
            case 6:
                allocationAlgorithm = "LFU";
                break;
            case 7:
                allocationAlgorithm = "MRU";
                break;
            case 8:
                allocationAlgorithm = "worstFit";
                break;
            case 9:
                allocationAlgorithm = "bestFit";
                break;
            case 10:
                allocationAlgorithm = "fifo";
                break;

            default:
                System.out.println("Invalid choice. Using First Fit by default.");
                allocationAlgorithm = "First Fit";
        }

        memoryManager.allocateMemory(sizeToAllocate, allocationAlgorithm);
    }
}
