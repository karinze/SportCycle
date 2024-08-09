import 'package:project4flutter/model/Users.dart';

/// external_token_id : 1
/// refresh_token : "d"
/// access_token : "f"
/// token_expires : "f"
/// created_dt : "12/12/2004"

class ExternalTokens {
  ExternalTokens({
    int? externalTokenId,
    required String? refreshToken,
    required String? accessToken,
    required String? tokenExpires,
    DateTime? createdDt,
    required Users? user,
  }) {
    _externalTokenId = externalTokenId;
    _refreshToken = refreshToken;
    _accessToken = accessToken;
    _tokenExpires = tokenExpires;
    _createdDt = createdDt ?? DateTime.now();
    _user = user;
  }

  ExternalTokens.fromJson(dynamic json) {
    _externalTokenId = json['external_token_id'];
    _refreshToken = json['refresh_token'];
    _accessToken = json['access_token'];
    _tokenExpires = json['token_expires'];
    _createdDt = _parseDate(json['created_dt']);
    _user = json['user'];
  }
  int? _externalTokenId;
  String? _refreshToken;
  String? _accessToken;
  String? _tokenExpires;
  DateTime? _createdDt;
  Users? _user;

  int get externalTokenId => _externalTokenId!;
  String get refreshToken => _refreshToken!;
  String get accessToken => _accessToken!;
  String get tokenExpires => _tokenExpires!;
  String get createdDt => _formatDate(_createdDt)!;
  Users get user => _user!;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['external_token_id'] = _externalTokenId;
    map['refresh_token'] = _refreshToken;
    map['access_token'] = _accessToken;
    map['token_expires'] = _tokenExpires;
    map['created_dt'] = _formatDate(_createdDt) ?? "";
    map['user'] = _user;
    return map;
  }

  static DateTime? _parseDate(String? dateStr) {
    if (dateStr == null || dateStr.isEmpty) return null;
    try {
      return DateTime.parse(dateStr);
    } catch (e) {
      print('Error parsing date: $e');
      return null;
    }
  }

  static String? _formatDate(DateTime? date) {
    if (date == null) return null;
    return date.toIso8601String();
  }
}
