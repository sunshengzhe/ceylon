function(from, to) {
    if (from > to) {
        var arr = this.measure(to, from-to+1);
        arr.reverseInPlace();
        return $arr$(arr,this.$$targs$$.Element$Array);
    }
    return this.measure(from, to-from+1);
}
