
function f(s, x, y, z) {
		switch (s) {
		case 0:
			return (x & y) ^ (~x & z);
		case 1:
			return x ^ y ^ z;
		case 2:
			return (x & y) ^ (x & z) ^ (y & z);
		case 3:
			return x ^ y ^ z;
		}
}


function rotl(x, n) {
		return (x << n) | (x >>> (32 - n));
}


function tohs(str) {
		var s = "",
		v;
		for (var i = 7; i >= 0; i--) {
			v = (str >>> (i * 4)) & 0xf;
			s += v.toString(16);
		}
		return s;
}


function h(msg) {
		var K = [0x5a827999, 0x6ed9eba1, 0x8f1bbcdc, 0xca62c1d6];
		msg += String.fromCharCode(0x80);
		var l = msg.length / 4 + 2;
		var N = Math.ceil(l / 16);
		var M = new Array(N);
		for (var i = 0; i < N; i++) {
			M[i] = new Array(16);
			for (var j = 0; j < 16; j++) {
				M[i][j] = (msg.charCodeAt(i * 64 + j * 4) << 24) | (msg.charCodeAt(i * 64 + j * 4 + 1) << 16) | (msg.charCodeAt(i * 64 + j * 4 + 2) << 8) | (msg.charCodeAt(i * 64 + j * 4 + 3));
			}
		}
		M[N - 1][14] = ((msg.length - 1) * 8) / Math.pow(2, 32);
		M[N - 1][14] = Math.floor(M[N - 1][14]);
		M[N - 1][15] = ((msg.length - 1) * 8) & 0xffffffff;
		var H0 = 0x67452301;
		var H1 = 0xefcdab89;
		var H2 = 0x98badcfe;
		var H3 = 0x10325476;
		var H4 = 0xc3d2e1f0;
		var W = new Array(80);
		var a, b, c, d, e;
		for (var i = 0; i < N; i++) {
			for (var t = 0; t < 16; t++) W[t] = M[i][t];
			for (var t = 16; t < 80; t++) W[t] = rotl(W[t - 3] ^ W[t - 8] ^ W[t - 14] ^ W[t - 16], 1);
			a = H0;
			b = H1;
			c = H2;
			d = H3;
			e = H4;
			for (var t = 0; t < 80; t++) {
				var s = Math.floor(t / 20);
				var T = (rotl(a, 5) + f(s, b, c, d) + e + K[s] + W[t]) & 0xffffffff;
				e = d;
				d = c;
				c = rotl(b, 30);
				b = a;
				a = T;
			}
			H0 = (H0 + a) & 0xffffffff;
			H1 = (H1 + b) & 0xffffffff;
			H2 = (H2 + c) & 0xffffffff;
			H3 = (H3 + d) & 0xffffffff;
			H4 = (H4 + e) & 0xffffffff;
		}
		return tohs(H0) + tohs(H1) + tohs(H2) + tohs(H3) + tohs(H4);
}


function uen(str) {
  str = (str + '').toString();
	return encodeURIComponent(str).replace(/!/g, '%21').replace(/'/g, '%27').replace(/\(/g, '%28').replace(/\)/g, '%29').replace(/\*/g, '%2A').replace(/%20/g, '+').replace(/~/g, '%7E');
}


function en(p, key) {
		if (p == "") {
			return "";
		}

		var v = sl(p, true);
		var k = sl(key, false);
		if (k.length < 4) {
			k.length = 4;
		}
		var n = v.length - 1;
		var z = v[n],
		y = v[0],
		de = 2654435769;
		var mx, e, p, q = Math.floor(6 + 52 / (n + 1)),
		sum = 0;
		while (0 < q--) {
			sum = sum + de & 0xffffffff;
			e = sum >>> 2 & 3;
			for (p = 0; p < n; p++) {
				y = v[p + 1];
				mx = (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
				z = v[p] = v[p] + mx & 0xffffffff;
			}
			y = v[0];
			mx = (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			z = v[n] = v[n] + mx & 0xffffffff;
		}
		return bh(v);
}


function sl(s, w) {
		var len = s.length;
		var v = [];
		for (var i = 0; i < len; i += 4) {
			v[i >> 2] = s.charCodeAt(i) | s.charCodeAt(i + 1) << 8 | s.charCodeAt(i + 2) << 16 | s.charCodeAt(i + 3) << 24;
		}
		if (w) {
			v[v.length] = len;
		}
		return v;
}


function bh(ar) {
		var charHex = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
		var str = "";
		var len = ar.length;
		
		for (var i = 0, tmp = len << 2; i < tmp; i++) {
			str += charHex[((ar[i >> 2] >> (((i & 3) << 3) + 4)) & 0xF)] + charHex[((ar[i >> 2] >> ((i & 3) << 3)) & 0xF)];
		}
		return str;
}

function getPassword(oriPassword, key) {
	return h(en(oriPassword, key));
}
