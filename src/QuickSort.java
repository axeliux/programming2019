
/*
Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
*/
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        //Arrays.sort(nums);
        shuffle(nums);
        quickSort(nums, 0, nums.length - 1, target);
        return nums[target];
    }
    
    private Integer quickSort(int [] nums, int i, int  j, int k) {
        if (i > j) return null;
        
        int p = partition(nums, i, j);
       
        if (p == k) return p;
        
        //System.out.println(Arrays.toString(nums));
        Integer x = quickSort(nums, i, p - 1, k);
        Integer y = quickSort(nums, p + 1, j, k);
        
        if (x != null) return x;
        else return y;
    }

    private void shuffle(int [] nums) {
        Random r = new Random();
        for (int i = 1; i < nums.length; i++) {
            int randIndex = r.nextInt(i);
            swap(nums, randIndex, i);
        }
    }
    
    private int partition(int [] nums, int low, int hi) {
        int pivot = nums [low];
        int pivotIndex = low;
        low ++;
        
        while (low <= hi) {
            while (low <= hi && nums[low] <= pivot) low++;
            while (low <= hi && nums[hi] > pivot) hi--;
            if (low < hi)
                swap(nums, low, hi);
        }
        swap(nums, pivotIndex, hi);
        
        return hi;
    }
    
    private void swap(int [] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }
}
